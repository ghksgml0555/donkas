package Graduation.donkas.controller;

import Graduation.donkas.responseResult.ResponseResult;
import Graduation.donkas.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/Wallet")
@Tag(name="지갑 관련")
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/getAllWallet")
    @Operation(summary = "모든 지갑 내용을 확인")
    public ResponseResult<?> getAllWallet() throws Exception {

        return ResponseResult.body(walletService.getAllWallet());
    }

    @PostMapping("/myWallet")
    @Operation(summary = "로그인 된 사용자의 지갑 내용 확인")
    public ResponseResult<?> myWallet(@AuthenticationPrincipal UserDetails user) throws Exception {
        log.info(user.getUsername()); //로그인한 member의 id
        return ResponseResult.body(walletService.myWallet(user.getUsername()));
    }

    @PostMapping("/transferMoneyTest")
    public ResponseResult<?> transferMoney(String senderId, String receiverId, int money) throws Exception {
        walletService.transferMoney(senderId, receiverId, money);
        return ResponseResult.body(walletService.getAllWallet());
    }
}
