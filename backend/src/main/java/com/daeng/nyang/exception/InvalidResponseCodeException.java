package com.daeng.nyang.exception;

import com.daeng.nyang.common.ResponseCode;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class InvalidResponseCodeException extends DaengNyangException {

    public InvalidResponseCodeException() {
        super(ResponseCode.RESPONSE_CODE_NOT_FOUND);
    }

    public InvalidResponseCodeException(ResponseCode responseCode) {
        super(responseCode);
    }

}