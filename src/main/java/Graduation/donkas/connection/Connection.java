package Graduation.donkas.connection;

import Graduation.donkas.dto.BookingDto.BookingDto;
import Graduation.donkas.dto.PlaceDto.PlaceDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import lombok.Data;
import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.CommitStatusException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.GatewayException;
import org.hyperledger.fabric.client.SubmitException;
import org.hyperledger.fabric.client.identity.Identities;
import org.hyperledger.fabric.client.identity.Identity;
import org.hyperledger.fabric.client.identity.Signer;
import org.hyperledger.fabric.client.identity.Signers;
import org.hyperledger.fabric.client.identity.X509Identity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Data
public class Connection {
    private static final String MSP_ID = System.getenv().getOrDefault("MSP_ID", "Org1MSP");
    private static final String CHANNEL_NAME = System.getenv().getOrDefault("CHANNEL_NAME", "booking");
    private static final String CHAINCODE_NAME = System.getenv().getOrDefault("CHAINCODE_NAME", "bookingCC");

    // Path to crypto materials.
    private static final Path CRYPTO_PATH = Paths.get("C:\\Windows\\System32\\testblockchain4\\newNetwork\\fabric-samples\\test-network\\organizations\\peerOrganizations\\org1.example.com");
    //private static final Path CRYPTO_PATH = Paths.get("../../test-network/organizations/peerOrganizations/org1.example.com");
    // Path to user certificate.
    private static final Path CERT_DIR_PATH = CRYPTO_PATH.resolve(Paths.get("users/User1@org1.example.com/msp/signcerts"));
    // Path to user private key directory.
    private static final Path KEY_DIR_PATH = CRYPTO_PATH.resolve(Paths.get("users/User1@org1.example.com/msp/keystore"));
    // Path to peer tls certificate.
    private static final Path TLS_CERT_PATH = CRYPTO_PATH.resolve(Paths.get("peers/peer0.org1.example.com/tls/ca.crt"));

    // Gateway peer end point.
    private static final String PEER_ENDPOINT = "localhost:7051";
    private static final String OVERRIDE_AUTH = "peer0.org1.example.com";

    private final Contract contract;
    private final String assetId = "asset" + Instant.now().toEpochMilli();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Connection() throws Exception {
        // The gRPC client connection should be shared by all Gateway connections to
        // this endpoint.
        var channel = newGrpcConnection();

        var builder = Gateway.newInstance().identity(newIdentity()).signer(newSigner()).connection(channel)
                // Default timeouts for different gRPC calls
                .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));
        var gateway = builder.connect();
        var network = gateway.getNetwork(CHANNEL_NAME);
        this.contract = network.getContract(CHAINCODE_NAME);

    }

    private static ManagedChannel newGrpcConnection() throws IOException {
        var credentials = TlsChannelCredentials.newBuilder()
                .trustManager(TLS_CERT_PATH.toFile())
                .build();
        return Grpc.newChannelBuilder(PEER_ENDPOINT, credentials)
                .overrideAuthority(OVERRIDE_AUTH)
                .build();
    }

    private static Identity newIdentity() throws IOException, CertificateException {
        try (var certReader = Files.newBufferedReader(getFirstFilePath(CERT_DIR_PATH))) {
            var certificate = Identities.readX509Certificate(certReader);
            return new X509Identity(MSP_ID, certificate);
        }
    }

    private static Signer newSigner() throws IOException, InvalidKeyException {
        try (var keyReader = Files.newBufferedReader(getFirstFilePath(KEY_DIR_PATH))) {
            var privateKey = Identities.readPrivateKey(keyReader);
            return Signers.newPrivateKeySigner(privateKey);
        }
    }

    private static Path getFirstFilePath(Path dirPath) throws IOException {
        try (var keyFiles = Files.list(dirPath)) {
            return keyFiles.findFirst().orElseThrow();
        }
    }


    public void run() throws GatewayException, CommitException {
        // Initialize a set of asset data on the ledger using the chaincode 'InitLedger' function.
        initLedger();


        GetAllBookings();
		/*// Return all the current assets on the ledger.
		getAllAssets();

		// Create a new asset on the ledger.
		createAsset();

		// Update an existing asset asynchronously.
		transferAssetAsync();

		// Get the asset details by assetID.
		readAssetById();

		// Update an asset which does not exist.
		updateNonExistentAsset();*/
    }

    /**
     * This type of transaction would typically only be run once by an application
     * the first time it was started after its initial deployment. A new version of
     * the chaincode deployed later would likely not need to run an "init" function.
     */
    public void initLedger() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: InitLedger, function creates the initial set of assets on the ledger");

        contract.submitTransaction("InitLedger");

        System.out.println("*** Transaction committed successfully");
    }

    public JsonElement GetAllBookings() throws GatewayException {
        System.out.println("\n--> Submit Transaction: GetAllBookings");

        var result = contract.evaluateTransaction("GetAllBookings");

        return prettyJson(result);
    }

    public JsonElement GetAllPlaces() throws GatewayException {
        System.out.println("\n--> Submit Transaction: GetAllPlaces");

        var result = contract.evaluateTransaction("GetAllPlaces");

        return prettyJson(result);
    }

    public JsonElement GetAllWallets() throws GatewayException {
        System.out.println("\n--> Submit Transaction: GetAllWallets");

        var result = contract.evaluateTransaction("GetAllWallets");

        return prettyJson(result);
    }

    public JsonElement GetBookingById(String bookingId) throws GatewayException {
        System.out.println("\n--> Submit Transaction: GetBookingById");

        var result = contract.evaluateTransaction("GetBookingById", bookingId);

        return prettyJson(result);
    }

    public JsonElement GetPlaceById(String placeId) throws GatewayException {
        System.out.println("\n--> Submit Transaction: GetPlaceById");

        var result = contract.evaluateTransaction("GetPlaceById", placeId);

        return prettyJson(result);
    }

    /**
     * Evaluate a transaction to query ledger state.
     */
    private void getAllAssets() throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: GetAllAssets, function returns all the current assets on the ledger");

        var result = contract.evaluateTransaction("GetAllAssets");

        System.out.println("*** Result: " + prettyJson(result));
    }



    private JsonElement prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private JsonElement prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return JsonParser.parseString(json);
    }

    /**
     * Submit a transaction synchronously, blocking until it has been committed to
     * the ledger.
     */
    private void createAsset() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateAsset, creates new asset with ID, Color, Size, Owner and AppraisedValue arguments");

        contract.submitTransaction("CreateAsset", assetId, "yellow", "5", "Tom", "1300");

        System.out.println("*** Transaction committed successfully");
    }

    public void createPlace(PlaceDto placeDto) throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateAsset, creates new asset with ID, Color, Size, Owner and AppraisedValue arguments");
        contract.submitTransaction("CreatePlace", placeDto.getPlaceID(), placeDto.getHostID(), placeDto.getAddress(),
                placeDto.getLocation(),placeDto.getBookingAvailable(),placeDto.getBookingPrice(),placeDto.getRating(), placeDto.getBusinessNumber());

        System.out.println("*** Transaction committed successfully");
    }

    public void createBooking(BookingDto bookingDto) throws EndorseException, SubmitException, CommitStatusException, CommitException {
        System.out.println("\n--> Submit Transaction: CreateAsset, creates new asset with ID, Color, Size, Owner and AppraisedValue arguments");
        contract.submitTransaction("CreateBooking",bookingDto.getBookingID(), bookingDto.getPlaceID(), bookingDto.getHostID(), bookingDto.getGuestID(),
                bookingDto.getCheckinDate(), bookingDto.getCheckoutDate(), bookingDto.getBookingStatus());

        System.out.println("*** Transaction committed successfully");
    }

    public void createWallet(String id) throws EndorseException, CommitException, SubmitException, CommitStatusException {
        System.out.println("\n--> Submit Transaction: CreateWallet");
        contract.submitTransaction("CreateWallet", id);
        System.out.println("*** Transaction committed successfully");

    }


    /**
     * Submit transaction asynchronously, allowing the application to process the
     * smart contract response (e.g. update a UI) while waiting for the commit
     * notification.
     */
    private void transferAssetAsync() throws EndorseException, SubmitException, CommitStatusException {
        System.out.println("\n--> Async Submit Transaction: TransferAsset, updates existing asset owner");

        var commit = contract.newProposal("TransferAsset")
                .addArguments(assetId, "Saptha")
                .build()
                .endorse()
                .submitAsync();

        var result = commit.getResult();
        var oldOwner = new String(result, StandardCharsets.UTF_8);

        System.out.println("*** Successfully submitted transaction to transfer ownership from " + oldOwner + " to Saptha");
        System.out.println("*** Waiting for transaction commit");

        var status = commit.getStatus();
        if (!status.isSuccessful()) {
            throw new RuntimeException("Transaction " + status.getTransactionId() +
                    " failed to commit with status code " + status.getCode());
        }

        System.out.println("*** Transaction committed successfully");
    }


    private void readAssetById() throws GatewayException {
        System.out.println("\n--> Evaluate Transaction: ReadAsset, function returns asset attributes");

        var evaluateResult = contract.evaluateTransaction("ReadAsset", assetId);

        System.out.println("*** Result:" + prettyJson(evaluateResult));
    }


    /**
     * submitTransaction() will throw an error containing details of any error
     * responses from the smart contract.
     */
    private void updateNonExistentAsset() {
        try {
            System.out.println("\n--> Submit Transaction: UpdateAsset asset70, asset70 does not exist and should return an error");

            contract.submitTransaction("UpdateAsset", "asset70", "blue", "5", "Tomoko", "300");

            System.out.println("******** FAILED to return an error");
        } catch (EndorseException | SubmitException | CommitStatusException e) {
            System.out.println("*** Successfully caught the error: ");
            e.printStackTrace(System.out);
            System.out.println("Transaction ID: " + e.getTransactionId());

            var details = e.getDetails();
            if (!details.isEmpty()) {
                System.out.println("Error Details:");
                for (var detail : details) {
                    System.out.println("- address: " + detail.getAddress() + ", mspId: " + detail.getMspId()
                            + ", message: " + detail.getMessage());
                }
            }
        } catch (CommitException e) {
            System.out.println("*** Successfully caught the error: " + e);
            e.printStackTrace(System.out);
            System.out.println("Transaction ID: " + e.getTransactionId());
            System.out.println("Status code: " + e.getCode());
        }
    }

    public void accept(BookingDto bookingDto) throws Exception {
        contract.submitTransaction("UpdateBookingStatus",bookingDto.getBookingID(),
                bookingDto.getHostID(),"예약승인");
    }

    public void refuse(BookingDto bookingDto) throws Exception {
        contract.submitTransaction("UpdateBookingStatus",bookingDto.getBookingID(),
                bookingDto.getHostID(),"예약거절");
    }

    public void cancel(BookingDto bookingDto) throws Exception {
        contract.submitTransaction("UpdateBookingStatus",bookingDto.getBookingID(),
                bookingDto.getHostID(),"예약취소");
    }

    public void TransferMoney(String senderId, String receiverId,int amount) throws Exception{
        contract.submitTransaction("TransferMoney", senderId, receiverId, String.valueOf(amount));
    }




}

