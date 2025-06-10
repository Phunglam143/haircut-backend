package com.haircut.utils;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String token;

    public ApiResponse(boolean success, String message, T data, String token) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public ApiResponse(boolean success, String message, T data) {
        this(success, message, data, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data, null);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null);
    }

    public static <T> ApiResponse<T> success(String message, T data, String token) {
        return new ApiResponse<>(true, message, data, token);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getToken() {
        return token;
    }
} 