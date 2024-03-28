package Graduation.donkas.controller;

import Graduation.donkas.dto.BookingDto;
import Graduation.donkas.dto.MemberRequestDto;
import Graduation.donkas.dto.PlaceDto;
import Graduation.donkas.responseResult.ResponseResult;
import Graduation.donkas.service.AuthService;
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

    /*@PostMapping("/registration")
    public ResponseResult<?> registration() throws Exception {

        //return ResponseResult.body(placeService.registration());
    }*/

    @PostMapping("/LeaseRequest")
    public ResponseResult<?> LeaseRequest(@RequestBody BookingDto bookingDto) throws Exception {
        //bookingStatus = 대여신청
        return ResponseResult.body(bookingService.LeaseRequest(bookingDto));
    }

    @PostMapping("/accept")
    public ResponseResult<?> accept(@RequestBody BookingDto bookingDto) throws Exception {
        //bookingStatus = 수락
        return ResponseResult.body(bookingService.accept(bookingDto));
    }

    @PostMapping("/myLease")
    public ResponseResult<?> myLease(@AuthenticationPrincipal UserDetails user) throws Exception {
        log.info(user.getUsername()); //로그인한 member의 id
        return ResponseResult.body(bookingService.myLease(user.getUsername()));
    }
}
