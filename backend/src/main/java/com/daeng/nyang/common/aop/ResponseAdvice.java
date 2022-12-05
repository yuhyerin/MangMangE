package com.daeng.nyang.common.aop;

import com.daeng.nyang.common.DaengNyangResponse;
import com.daeng.nyang.common.ResponseCode;
import com.daeng.nyang.exception.DaengNyangException;
import com.daeng.nyang.exception.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ResponseAdvice {

    /** 예상치 못한 런타임에러 */
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<DaengNyangResponse<?>> handleRuntimeException(RuntimeException e){
        log.error("[" + e.getClass() + "] " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DaengNyangResponse.createError(ResponseCode.RESPONSE_CODE_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(DaengNyangException.class)
    ResponseEntity<DaengNyangResponse<?>> handleTugetherException(DaengNyangException e){
        log.error("[" + e.getClass() + "] " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DaengNyangResponse.createError(e.getMessage()));
    }

    /**
     * 반드시 처리해줘야 하는 CheckedException
     * */



    /**
     *  - UncheckedException 이라서 굳이 처리해 줄 필요는 없지만
     *  - 커스텀 메세지를 전달해서 처리해 주고 싶을때 이곳에 작성하면 된다.
     */

    /**
     * UserAlreadyExist
     * 이미 존재하는 사용자입니다.
     * */
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<DaengNyangResponse<?>> handleUserAlreadyExist(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.OK).body(DaengNyangResponse.createError(e.getMessage()));
    }
}
