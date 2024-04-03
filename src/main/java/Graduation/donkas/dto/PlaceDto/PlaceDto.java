package Graduation.donkas.dto.PlaceDto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDto {

    String placeID;
    String hostID;
    String address;
    String location;
    String bookingAvailable;
    String rating;
    String businessNumber;
    String bookingPrice;

    public PlaceDto(JsonObject asset){
        this.placeID = asset.get("placeID").getAsString();
        this.hostID = asset.get("hostID").getAsString();
        this.address = asset.get("address").getAsString();
        this.location = asset.get("location").getAsString();
        this.bookingAvailable = asset.get("bookingAvailable").getAsString();
        this.rating = asset.get("rating").getAsString();
        this.businessNumber = asset.get("businessNumber").getAsString();
        this.bookingPrice = asset.get("bookingPrice").getAsString();
    }


}
