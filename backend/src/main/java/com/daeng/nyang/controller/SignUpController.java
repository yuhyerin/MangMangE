package com.daeng.nyang.controller;

import com.daeng.nyang.common.DaengNyangResponse;
import com.daeng.nyang.controller.dto.*;
import com.daeng.nyang.service.signup.SignupService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class SignUpController {

    private final SignupService signupService;

    @PostMapping(path = "/newuser/signup")
    @ApiOperation("회원가입")
    public DaengNyangResponse<AccountResponseDto> signup(@RequestBody AccountRequestDto account) {
        AccountResponseDto result = signupService.signup(account);
        return DaengNyangResponse.createSuccess(result);
    }

    @GetMapping(path = "/newuser/signup/{userId}")
    @ApiOperation("아이디 중복 검사")
    public DaengNyangResponse<IdCheckResponseDto> checkID(@PathVariable String userId) {
        return DaengNyangResponse.createSuccess(signupService.checkID(userId));
    }

    @GetMapping(path = "/newuser/signup")
    @ApiOperation("이메일 인증번호 전송 요청")
    public DaengNyangResponse<EmailCheckResponseDto> checkEmail(@RequestParam String email) {
        return DaengNyangResponse.createSuccess(signupService.checkEmail(email));
    }

    @GetMapping(path = "/newuser/signup/hashcheck")
    @ApiOperation("인증번호 유효성 검사")
    public DaengNyangResponse<AuthNumberCheckResponseDto> checkAuthNumber(@RequestParam String authNumber, @RequestParam String hashNumber) {
        return DaengNyangResponse.createSuccess(signupService.checkAuthNumber(authNumber, hashNumber));
    }

}