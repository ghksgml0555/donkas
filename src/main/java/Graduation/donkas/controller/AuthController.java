package Graduation.donkas.controller;


import Graduation.donkas.dto.LoginDto;
import Graduation.donkas.dto.MemberRequestDto;
import Graduation.donkas.repository.MemberRepository;
import Graduation.donkas.responseResult.ResponseResult;
import Graduation.donkas.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseResult<?> signup(@RequestBody MemberRequestDto memberRequestDto) {

        return ResponseResult.body(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody LoginDto loginDto) {
        return ResponseResult.body(authService.login(loginDto));
    }

}

