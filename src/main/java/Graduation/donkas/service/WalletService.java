package Graduation.donkas.service;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.BookingDto;
import Graduation.donkas.dto.PlaceDto;
import Graduation.donkas.dto.WalletDto;
import Graduation.donkas.responseResult.ResponseResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    public List<WalletDto> getAllWallet() throws Exception {
        Connection connection = new Connection();
        JsonArray wallets = connection.GetAllWallets().getAsJsonArray();
        List<WalletDto> walletDtos = new ArrayList<>();
        for(int i=0;i<wallets.size();++i){
            JsonObject asset = wallets.get(i).getAsJsonObject();
            WalletDto walletDto = new WalletDto(asset);
            walletDtos.add(walletDto);
        }
        return walletDtos;
    }

    public List<WalletDto> myWallet(String id) throws Exception {
        Connection connection = new Connection();
        JsonArray wallets = connection.GetAllWallets().getAsJsonArray();
        List<WalletDto> walletDtos = new ArrayList<>();
        for(int i=0;i<wallets.size();++i){
            JsonObject asset = wallets.get(i).getAsJsonObject();
            WalletDto walletDto = new WalletDto(asset);
            if(walletDto.getUserID().equals(id)){
                walletDtos.add(walletDto);
            }
        }
        return walletDtos;
    }

    public boolean transferMoney(String senderId, String receiverId, int money) throws Exception {
        Connection connection = new Connection();
        try{
            connection.TransferMoney(senderId, receiverId, money);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
