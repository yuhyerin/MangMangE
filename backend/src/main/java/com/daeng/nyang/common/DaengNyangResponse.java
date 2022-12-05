package com.daeng.nyang.common;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class DaengNyangResponse<T> {
    private static final boolean SUCCESS = true;
    private static final boolean FAIL = false;
    private static final boolean ERROR = false;

    /** 정상(success)
     * 예외(error)
     * 오류(fail)
     * 중 한 값을 갖는다. */
    private boolean status;

    /** 정상(success)의 경우 실제 전송될 데이터
     * 오류(fail)의 경우 유효성 검증에 실패한 데이터 */
    private T data;

    /** 예외(error)의 경우 예외 메세지를 응답한다.*/
    private String message;

    public static <T> DaengNyangResponse<T> createSuccess(T data) {
        return new DaengNyangResponse<>(SUCCESS, data, "ok");
    }

    public static DaengNyangResponse<?> createSuccessWithNoContent() {
        return new DaengNyangResponse<>(SUCCESS, null, "ok");
    }

    // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
    public static DaengNyangResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new DaengNyangResponse<>(FAIL, errors, null);
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static DaengNyangResponse<?> createError(String message) {
        return new DaengNyangResponse<>(ERROR, null, message);
    }

    private DaengNyangResponse(boolean status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
