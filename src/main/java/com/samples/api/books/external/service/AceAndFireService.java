package com.samples.api.books.external.service;

import com.samples.api.books.external.model.Book;

import java.util.List;

public interface AceAndFireService {
    List<Book> getAllBooks();

    List<Book> getAllBooksByName(String name);
}
