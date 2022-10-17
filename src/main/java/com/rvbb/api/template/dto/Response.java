package com.rvbb.api.template.dto;

import com.rvbb.api.template.controller.handler.Error;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "error code")
    private Error status;
    @ApiModelProperty(notes = "data of response")
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
