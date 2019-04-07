package com.samples.api.books.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse<T> {
    @JsonProperty("status_code")
    private Integer statusCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private T data;

    static <T> BookResponse<T> ok(T data) {
        return new BookResponse<>(HttpStatus.OK.value(), "success", data);
    }

    static <T> BookResponse<T> created(T data) {
        return new BookResponse<>(HttpStatus.CREATED.value(), "success", data);
    }
}
