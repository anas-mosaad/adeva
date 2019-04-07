package com.samples.api.books.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samples.api.books.dto.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
class BookResponse {
    @JsonProperty("status_code")
    private Integer statusCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private List<Book> books;

}
