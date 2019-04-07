package com.samples.api.books.internal.service;

import com.samples.api.books.internal.model.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);

    Book read(Long id);

    List<Book> read(String name, String country, String publisher, Integer year);

    Book update(Book oldBook, Book newBook);

    Book delete(Long id);
}
