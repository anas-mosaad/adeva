package com.samples.api.books.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseWithMessage<T> {
    @JsonProperty("status_code")
    private Integer statusCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    static <T> BookResponseWithMessage<T> ok(T data, String msg) {
        return new BookResponseWithMessage<>(HttpStatus.OK.value(), "success", msg, data);
    }

    static BookResponseWithMessage<?> error(int code, String status, String msg) {
        return new BookResponseWithMessage<>(code, status, msg, Collections.emptyList());
    }

    static BookResponseWithMessage<?> deleted(String msg) {
        return new BookResponseWithMessage<>(HttpStatus.NO_CONTENT.value(), "success", msg, Collections.emptyList());
    }

    static BookResponseWithMessage notFound(String msg) {
        return new BookResponseWithMessage<>(HttpStatus.NOT_FOUND.value(), "not found", msg, Collections.emptyList());
    }
}
