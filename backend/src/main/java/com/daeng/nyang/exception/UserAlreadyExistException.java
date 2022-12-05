package com.daeng.nyang.exception;

import com.daeng.nyang.common.ResponseCode;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class UserAlreadyExistException extends DaengNyangException {

    public UserAlreadyExistException() {
        super(ResponseCode.USER_ALREADY_EXIST);
    }

    public UserAlreadyExistException(ResponseCode responseCode) {
        super(responseCode);
    }
}