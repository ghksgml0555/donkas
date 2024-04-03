package Graduation.donkas.controller;

import Graduation.donkas.dto.BookingDto.BookingDto;
import Graduation.donkas.dto.BookingDto.BookingRequestDto;
import Graduation.donkas.dto.PlaceDto.PlaceDto;
import Graduation.donkas.dto.PlaceDto.PlaceRequestDto;
import Graduation.donkas.responseResult.ResponseResult;
import Graduation.donkas.service.BookingService;
import Graduation.donkas.service.PlaceService;
import Graduation.donkas.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/accommodation")
public class PlaceController {
    private final PlaceService placeService;
    private final BookingService bookingService;
    private final WalletService walletService;

    @PostMapping("/search")
    public ResponseResult<?> search(@RequestParam String address) throws Exception {
        return ResponseResult.body(placeService.searchByAddress(address));
    }

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
    public ResponseResult<?> accept(@RequestBody String bookingID) throws Exception {
        BookingDto bookingDto = bookingService.GetBookingById(bookingID);
        PlaceDto placeDto = placeService.GetPlaceById(bookingDto.getPlaceID());
        //bookingStatus = 예약승인
        //체크인 체크아웃 날짜차이 계산해서 price에 곱해줘야함 >> 수정필요
        walletService.transferMoney(bookingDto.getGuestID(), bookingDto.getHostID(),Integer.valueOf(placeDto.getBookingPrice()));
        return ResponseResult.body(bookingService.accept(bookingDto));
    }

    @PostMapping("/refuse")
    public ResponseResult<?> refuse(@RequestBody String bookingID) throws Exception {
        //bookingStatus = 예약거절
        BookingDto bookingDto = bookingService.GetBookingById(bookingID);
        return ResponseResult.body(bookingService.refuse(bookingDto));
    }

    @PostMapping("/cancel")
    public ResponseResult<?> cancel(@RequestBody String bookingID) throws Exception {
        //bookingStatus = 예약취소
        BookingDto bookingDto = bookingService.GetBookingById(bookingID);
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

    @PostMapping("/myPlaceLeaseRequestList")
    public ResponseResult<?> myPlaceLeaseRequestList(@AuthenticationPrincipal UserDetails user) throws Exception {
        log.info(user.getUsername()); //로그인한 member의 id
        return ResponseResult.body(bookingService.myPlaceLeaseRequestList(user.getUsername()));
    }
}
