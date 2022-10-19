package com.rvbb.food.template.dto;

import com.rvbb.food.template.controller.handler.Error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Response<T> {
    @Schema(title = "error code")
    private Error status;
    @Schema(title = "data of response")
    private T data;

    public static <T> Response<T> ok(T data) {
        Error status = Error.builder().message("OK").code(HttpStatus.OK.value()).build();
        return Response.<T>builder().status(status).data(data).build();
    }

    public static <T> Response<T> fail(String message, int code) {
        return Response.<T>builder().status(Error.builder().message(message).code(code).build()).build();
    }

    public static <T> Response<T> fail(Error status) {
        return Response.<T>builder().status(status).build();
    }
}
