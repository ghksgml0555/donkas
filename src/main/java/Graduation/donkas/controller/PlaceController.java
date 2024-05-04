package Graduation.donkas.controller;

import Graduation.donkas.dto.VerifyHostDto;
import Graduation.donkas.dto.BookingDto.BookingDto;
import Graduation.donkas.dto.BookingDto.BookingRequestDto;
import Graduation.donkas.dto.PlaceDto.PlaceDto;
import Graduation.donkas.dto.PlaceDto.PlaceRequestDto;
import Graduation.donkas.responseResult.ResponseResult;
import Graduation.donkas.service.BookingService;
import Graduation.donkas.service.DateService;
import Graduation.donkas.service.PlaceService;
import Graduation.donkas.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="숙소/예약 관련")
public class PlaceController {
    private final PlaceService placeService;
    private final BookingService bookingService;
    private final WalletService walletService;
    private final DateService dateService;

    @GetMapping("/search")
    @Operation(summary = "지역으로 검색")
    public ResponseResult<?> search(@RequestParam String address) throws Exception {
        return ResponseResult.body(placeService.searchByAddress(address));
    }

    @PostMapping("/registration")
    @Operation(summary = "숙소 등록")
    public ResponseResult<?> registration(@RequestBody PlaceRequestDto placeRequestDto,@AuthenticationPrincipal UserDetails user) throws Exception {
        if (!placeService.verifyHost(placeRequestDto.toVerifyHostDto())) return ResponseResult.body("400", "존재하지 않는 사업자입니다.");
        PlaceDto placeDto = placeRequestDto.toPlaceDto(user.getUsername());
        return ResponseResult.body(placeService.registration(placeDto));
    }

    @PostMapping("/LeaseRequest")
    @Operation(summary = "숙박 신청", description = "숙소를 빌리려는 사람이 숙박을 신청한다.")
    public ResponseResult<?> LeaseRequest(@RequestBody BookingRequestDto bookingRequestDto, @AuthenticationPrincipal UserDetails user) throws Exception {
        //bookingStatus = 예약신청
        BookingDto bookingDto = bookingRequestDto.toBookingDto(user.getUsername());
        return ResponseResult.body(bookingService.LeaseRequest(bookingDto));
    }

    @PostMapping("/accept")
    @Operation(summary = "숙박 승인", description = "숙소 주인이 신청을 승인한다. 가격*일자 만큼 돈이 이동한다")
    public ResponseResult<?> accept(@RequestParam String bookingID) throws Exception {
        BookingDto bookingDto = bookingService.GetBookingById(bookingID);
        PlaceDto placeDto = placeService.GetPlaceById(bookingDto.getPlaceID());
        //bookingStatus = 예약승인
        //체크인 체크아웃 날짜차이 계산해서 price에 곱해줘야함 >> 수정필요
        long dayOfStay = dateService.calculateDayOfStay(bookingDto.getCheckinDate(), bookingDto.getCheckoutDate());
        int price = Integer.valueOf(placeDto.getBookingPrice()) * (int)dayOfStay;
        System.out.println((int)dayOfStay);
        System.out.println(Integer.valueOf(placeDto.getBookingPrice()));
        System.out.println(price);
        walletService.transferMoney(bookingDto.getGuestID(), bookingDto.getHostID(),price);
        return ResponseResult.body(bookingService.accept(bookingDto));
    }

    @PostMapping("/refuse")
    @Operation(summary = "숙박 거절", description = "숙소주인이 숙박 신청을 거절한다")
    public ResponseResult<?> refuse(@RequestParam String bookingID) throws Exception {
        //bookingStatus = 예약거절
        BookingDto bookingDto = bookingService.GetBookingById(bookingID);
        return ResponseResult.body(bookingService.refuse(bookingDto));
    }

    @PostMapping("/cancel")
    @Operation(summary = "숙박 취소", description = "숙소를 빌리려는 사람이 숙박 신청을 취소한다")
    public ResponseResult<?> cancel(@RequestParam String bookingID) throws Exception {
        //bookingStatus = 예약취소
        BookingDto bookingDto = bookingService.GetBookingById(bookingID);
        return ResponseResult.body(bookingService.cancel(bookingDto));
    }

    @GetMapping("/myLease")
    @Operation(summary = "내가 예약한 정보 확인", description = "내가 예약했던 목록들을 볼 수 있다")
    public ResponseResult<?> myLease(@AuthenticationPrincipal UserDetails user) throws Exception {
        log.info(user.getUsername()); //로그인한 member의 id
        return ResponseResult.body(bookingService.myLease(user.getUsername()));
    }

    @GetMapping("/myPlace")
    @Operation(summary = "내가 올린 숙소 확인", description = "집주인이 자기가 등록한 숙소들을 확인할 수 있다.")
    public ResponseResult<?> myPlace(@AuthenticationPrincipal UserDetails user) throws Exception {
        log.info(user.getUsername()); //로그인한 member의 id
        return ResponseResult.body(placeService.myPlace(user.getUsername()));
    }

    @GetMapping("/myPlaceLeaseRequestList")
    @Operation(summary = "내 숙소목록중 숙박 신청이 들어온 숙소 확인")
    public ResponseResult<?> myPlaceLeaseRequestList(@AuthenticationPrincipal UserDetails user) throws Exception {
        log.info(user.getUsername()); //로그인한 member의 id
        return ResponseResult.body(bookingService.myPlaceLeaseRequestList(user.getUsername()));
    }
}
