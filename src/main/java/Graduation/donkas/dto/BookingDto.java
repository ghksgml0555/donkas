package Graduation.donkas.dto;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    String bookingID;
    String placeID;
    String hostID;
    String guestID;
    String bookingPrice;
    String checkinDate;
    String checkoutDate;
    String bookingStatus;

    public BookingDto(JsonObject asset){
        this.bookingID = asset.get("bookingID").getAsString();
        this.placeID = asset.get("placeID").getAsString();
        this.hostID = asset.get("hostID").getAsString();
        this.guestID = asset.get("guestID").getAsString();
        this.bookingPrice = asset.get("bookingPrice").getAsString();
        this.checkinDate = asset.get("checkinDate").getAsString();
        this.checkoutDate = asset.get("checkoutDate").getAsString();
        this.bookingStatus = asset.get("bookingStatus").getAsString();
    }
}
