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

    String address;
    String bookingPrice;

    public PlaceDto toPlaceDto(String id) {

        String uniqueId = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar dateTime = Calendar.getInstance();
        uniqueId = sdf.format(dateTime.getTime());

        uniqueId = id+"_"+uniqueId+"_"+ RandomStringUtils.randomAlphanumeric(6);

        return PlaceDto.builder()
                .placeID(uniqueId)
                .hostID(id)
                .address(address)
                .location("00")
                .bookingAvailable("true")
                .rating("0.0")
                .businessNumber("businessNumber")
                .bookingPrice(bookingPrice)
                .build();
    }
}
