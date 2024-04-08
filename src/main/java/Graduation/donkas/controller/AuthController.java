package Graduation.donkas.controller;


import Graduation.donkas.dto.LoginDto;
import Graduation.donkas.dto.MemberDto.MemberRequestDto;
import Graduation.donkas.responseResult.ResponseResult;
import Graduation.donkas.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name="회원가입/로그인")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseResult<?> signup(@RequestBody MemberRequestDto memberRequestDto) throws Exception {

        return ResponseResult.body(authService.signup(memberRequestDto));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody LoginDto loginDto) {
        return ResponseResult.body(authService.login(loginDto));
    }

}

