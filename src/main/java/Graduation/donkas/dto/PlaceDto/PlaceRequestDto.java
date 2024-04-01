package Graduation.donkas.dto.PlaceDto;

import Graduation.donkas.domain.member.Authority;
import Graduation.donkas.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceRequestDto {

    String hostID;
    String address;
    String location;
    String bookingAvailable;
    String rating;
    String businessNumber;

    public PlaceDto toPlaceDto(String id) {

        String uniqueId = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar dateTime = Calendar.getInstance();
        uniqueId = sdf.format(dateTime.getTime());

        uniqueId = id+"_"+uniqueId+"_"+ RandomStringUtils.randomAlphanumeric(6);

        return PlaceDto.builder()
                .placeID(uniqueId)
                .hostID(hostID)
                .address(address)
                .location(location)
                .bookingAvailable(bookingAvailable)
                .rating(rating)
                .businessNumber(businessNumber)
                .build();
    }
}
