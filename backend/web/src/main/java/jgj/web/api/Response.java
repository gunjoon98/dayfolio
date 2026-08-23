package jgj.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Response<T> {

    @JsonProperty("result_code")
    private final String resultCode;

    private final T data;

    private final String message;

    public Response(String resultCode, T data) {
        this(resultCode, data, null);
    }

    public Response(String resultCode, T data, String message) {
        this.resultCode = resultCode;
        this.data = data;
        this.message = message;
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>("SUCCESS", data);
    }

    public static <T> Response<T> error() {
        return new Response<>("FAIL", null);
    }

    public static <T> Response<T> error(String code) {
        return new Response<>(code, null);
    }

    public static <T> Response<T> error(String code, String message) {
        return new Response<>(code, null, message);
    }
}