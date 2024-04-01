package Graduation.donkas.dto.BookingDto;


import Graduation.donkas.dto.PlaceDto.PlaceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    String placeID;
    String hostID;
    String guestID;
    String bookingPrice;
    String checkinDate;
    String checkoutDate;

    //예약신청, 예약승인, 예약거절, 예약취소
    String bookingStatus;


    public BookingDto toBookingDto(String id) {

        String uniqueId = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar dateTime = Calendar.getInstance();
        uniqueId = sdf.format(dateTime.getTime());

        uniqueId = id+"_"+uniqueId+"_"+ RandomStringUtils.randomAlphanumeric(6);

        return BookingDto.builder()
                .bookingID(uniqueId)
                .placeID(placeID)
                .hostID(hostID)
                .guestID(guestID)
                .bookingPrice(bookingPrice)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .bookingStatus(bookingStatus)
                .build();
    }
}
