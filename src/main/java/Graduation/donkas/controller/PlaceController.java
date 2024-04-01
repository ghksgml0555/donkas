package Graduation.donkas.controller;

import Graduation.donkas.dto.BookingDto.BookingDto;
import Graduation.donkas.dto.BookingDto.BookingRequestDto;
import Graduation.donkas.dto.PlaceDto.PlaceDto;
import Graduation.donkas.dto.PlaceDto.PlaceRequestDto;
import Graduation.donkas.responseResult.ResponseResult;
import Graduation.donkas.service.BookingService;
import Graduation.donkas.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/accommodation")
public class PlaceController {
    private final PlaceService placeService;
    private final BookingService bookingService;

    /*@PostMapping("/search")
    public ResponseResult<?> search(@RequestBody SearchDto searchDto) throws Exception {
        개발중
    }*/

    @PostMapping("/registration")
    public ResponseResult<?> registration(@RequestBody PlaceRequestDto placeRequestDto,@AuthenticationPrincipal UserDetails user) throws Exception {
        PlaceDto placeDto = placeRequestDto.toPlaceDto(user.getUsername());
        return ResponseResult.body(placeService.registration(placeDto));
    }

    @PostMapping("/LeaseRequest")
    public ResponseResult<?> LeaseRequest(@RequestBody BookingRequestDto bookingRequestDto, @AuthenticationPrincipal UserDetails user) throws Exception {
        //bookingStatus = 예약신청
        BookingDto bookingDto = bookingRequestDto.toBookingDto(user.getUsername());
        return ResponseResult.body(bookingService.LeaseRequest(bookingDto));
    }

    @PostMapping("/accept")
    public ResponseResult<?> accept(@RequestBody BookingDto bookingDto) throws Exception {
        //bookingStatus = 예약승인

        return ResponseResult.body(bookingService.accept(bookingDto));
    }

    @PostMapping("/refuse")
    public ResponseResult<?> refuse(@RequestBody BookingDto bookingDto) throws Exception {
        //bookingStatus = 예약거절

        return ResponseResult.body(bookingService.refuse(bookingDto));
    }

    @PostMapping("/cancel")
    public ResponseResult<?> cancel(@RequestBody BookingDto bookingDto) throws Exception {
        //bookingStatus = 예약취소

        return ResponseResult.body(bookingService.cancel(bookingDto));
    }

    @PostMapping("/myLease")
    public ResponseResult<?> myLease(@AuthenticationPrincipal UserDetails user) throws Exception {
        log.info(user.getUsername()); //로그인한 member의 id
        return ResponseResult.body(bookingService.myLease(user.getUsername()));
    }

    @PostMapping("/myPlace")
    public ResponseResult<?> myPlace(@AuthenticationPrincipal UserDetails user) throws Exception {
        log.info(user.getUsername()); //로그인한 member의 id
        return ResponseResult.body(placeService.myPlace(user.getUsername()));
    }
}
