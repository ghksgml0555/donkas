package Graduation.donkas.controller;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.domain.member.Member;
import Graduation.donkas.dto.BookingDto.BookingDto;
import Graduation.donkas.dto.BookingDto.BookingRequestDto;
import Graduation.donkas.dto.PlaceDto.PlaceDto;
import Graduation.donkas.repository.MemberRepository;
import Graduation.donkas.responseResult.ResponseResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name="test")
public class testController {

    private final MemberRepository memberRepository;

    @GetMapping("/test")
    @Operation(summary = "모든 예약목록")
    public ResponseResult<?> test() throws Exception {
        Connection connection = new Connection();
        connection.initLedger();
        JsonArray bookings = connection.GetAllBookings().getAsJsonArray();
        System.out.println(bookings);
        List<BookingDto> bookingDtos = new ArrayList<>();
        for(int i=0;i<bookings.size();++i){
            JsonObject asset = bookings.get(i).getAsJsonObject();
            BookingDto bookingDto = new BookingDto(asset);
            bookingDtos.add(bookingDto);
        }
        return ResponseResult.body(bookingDtos);
    }

    @GetMapping("/testPlace")
    @Operation(summary = "모든 숙소")
    public ResponseResult<?> testPlace() throws Exception {
        Connection connection = new Connection();
        connection.initLedger();
        JsonArray places = connection.GetAllPlaces().getAsJsonArray();
        System.out.println(places);
        List<PlaceDto> placeDtos = new ArrayList<>();
        for(int i=0;i<places.size();++i){
            JsonObject asset = places.get(i).getAsJsonObject();
            PlaceDto placeDto = new PlaceDto(asset);
            placeDtos.add(placeDto);
        }
        return ResponseResult.body(placeDtos);
    }

    @GetMapping("/loginMemberInfo")
    @Operation(summary = "로그인한 유저의 정보 (임대인/임차인 신분 확인 가능")
    public ResponseResult<?> loginMemberInfo(@AuthenticationPrincipal UserDetails user) throws Exception {
        Member member = memberRepository.findById(Long.valueOf(user.getUsername())).get();
        return ResponseResult.body(member);
    }
}
