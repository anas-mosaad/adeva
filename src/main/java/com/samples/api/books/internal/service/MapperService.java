package com.samples.api.books.internal.service;

import com.samples.api.books.internal.dto.Book;

import java.util.Collection;

public interface MapperService {
    com.samples.api.books.internal.model.Book toModel(Book book);
    Collection<com.samples.api.books.internal.model.Book> toModel(Collection<Book> book);

    Book toDTO(com.samples.api.books.internal.model.Book book);
    Collection<Book> toDTO(Collection<com.samples.api.books.internal.model.Book> book);
}
