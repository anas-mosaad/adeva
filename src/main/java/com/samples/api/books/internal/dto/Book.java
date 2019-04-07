package com.samples.api.books.internal.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Book {
    private Long id;

    private String name;

    private String isbn;

    private Set<String> authors = new HashSet<>();

    private Integer numberOfPages;

    private String publisher;

    private String country;

    private LocalDate releaseDate;
}
