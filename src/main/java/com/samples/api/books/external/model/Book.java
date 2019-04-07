package com.samples.api.books.external.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class Book {
    private String name;
    private String isbn;
    private String[] authors;
    @JsonProperty("number_of_pages")
    @JsonAlias("numberOfPages")
    private Integer numberOfPages;
    private String publisher;
    private String country;
    @JsonProperty("release_date")
    @JsonAlias("released")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;
}
