package Graduation.donkas.controller;

import Graduation.donkas.dto.MemberRequestDto;
import Graduation.donkas.dto.PlaceDto;
import Graduation.donkas.responseResult.ResponseResult;
import Graduation.donkas.service.AuthService;
import Graduation.donkas.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/registration")
    public ResponseResult<?> registration() throws Exception {

        return ResponseResult.body(placeService.registration());
    }
}
