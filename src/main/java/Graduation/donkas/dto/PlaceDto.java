package Graduation.donkas.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {

    String placeID;
    String hostID;
    String address;
    int location;
    //String[] dateList = new String[100];
    boolean bookingAvailable;
    float rating;
    String businessNumber;
    boolean verified;

    public PlaceDto(JsonObject asset){
        this.placeID = asset.get("placeID").getAsString();
        this.hostID = asset.get("hostID").getAsString();
        this.address = asset.get("address").getAsString();
        this.location = asset.get("location").getAsInt();
        JsonArray dataListJsonArray = asset.get("dateList").getAsJsonArray();
        /*for(int i=0;i<dataListJsonArray.size();++i){
            this.dateList[i] = dataListJsonArray.get(i).getAsString();
        }*/
        this.bookingAvailable = asset.get("bookingAvailable").getAsBoolean();
        this.rating = asset.get("rating").getAsFloat();
        this.businessNumber = asset.get("businessNumber").getAsString();
        this.verified = asset.get("verified").getAsBoolean();
    }
}
