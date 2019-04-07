package com.samples.api.books.internal.service;

import com.samples.api.books.internal.dto.Book;
import com.samples.api.books.internal.model.Author;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MapperServiceImpl implements MapperService {
    @Override
    public com.samples.api.books.internal.model.Book toModel(Book book) {
        com.samples.api.books.internal.model.Book result = new com.samples.api.books.internal.model.Book();
        result.setId(book.getId());
        result.setName(book.getName());
        result.setIsbn(book.getIsbn());
        result.setCountry(book.getCountry());
        result.setAuthors(book.getAuthors().stream().map(a -> new Author(a, result)).collect(Collectors.toSet()));
        result.setNumberOfPages(book.getNumberOfPages());
        result.setPublisher(book.getPublisher());
        result.setReleaseDate(book.getReleaseDate());
        return result;
    }

    @Override
    public Collection<com.samples.api.books.internal.model.Book> toModel(Collection<Book> books) {
        return books.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public Book toDTO(com.samples.api.books.internal.model.Book book) {
        Book result = new Book();
        result.setId(book.getId());
        result.setName(book.getName());
        result.setCountry(book.getCountry());
        result.setIsbn(book.getIsbn());
        result.setAuthors(book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()));
        result.setNumberOfPages(book.getNumberOfPages());
        result.setPublisher(book.getPublisher());
        result.setReleaseDate(book.getReleaseDate());
        return result;
    }

    @Override
    public Collection<Book> toDTO(Collection<com.samples.api.books.internal.model.Book> books) {
        return books.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
