package com.hoshuttle.backend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "요청 성공", data);
    }

    public static <T> BaseResponse<T> fail(String message) {
        return new BaseResponse<>(false, message, null);
    }
}
