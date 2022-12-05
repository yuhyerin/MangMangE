package com.daeng.nyang.exception;

import com.daeng.nyang.common.ResponseCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class DaengNyangException extends RuntimeException {

    private static final long serialVersionUID = -6110005653362559998L;

    private String message;

    public DaengNyangException() {
        super();
    }

    public DaengNyangException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.message = responseCode.getMessage();
    }

}
