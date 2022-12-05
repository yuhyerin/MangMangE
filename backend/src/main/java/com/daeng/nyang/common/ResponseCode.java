package com.daeng.nyang.common;

import com.daeng.nyang.exception.InvalidResponseCodeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    /** 200 */
    SUCCESS(HttpStatus.OK, 200, "OK"),

    /** 4xx */
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, 44090, "등록된 사용자 입니다."),

    /** 5xx */
    MAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, 55000,"메일 발송 중 에러가 발생했습니다."),


    /** 999 */
    RESPONSE_CODE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, 99999, "확인되지 않은 오류가 발생하였습니다.");

    private final HttpStatus httpStatus;

    private final int code;

    private final String message;

    public static ResponseCode getCode(int code) {
        return Arrays.stream(values()).findFirst().orElseThrow(InvalidResponseCodeException::new);
    }

}