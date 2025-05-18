package com.hoseobus.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "OK", "성공", data);
    }

    public static BaseResponse<?> fail(String code, String message) {
        return new BaseResponse<>(false, code, message, null);
    }
}
