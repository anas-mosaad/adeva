package com.samples.api.books.internal.service;

import com.samples.api.books.internal.exceptions.ResourceNotFoundException;
import com.samples.api.books.internal.model.Author;
import com.samples.api.books.internal.model.Book;
import com.samples.api.books.internal.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book create(Book book) {
        return repository.save(book);
    }

    @Override
    public Book read(Long id) {
        Optional<Book> book = repository.findById(id);
        if (!book.isPresent()) {
            throw new ResourceNotFoundException("Book with ID [" + id + "] is not found.");
        }
        return book.get();
    }

    @Override
    public List<Book> read() {
        return repository.findAll();
    }

    @Override
    public Book update(Book old, Book newBook) {
        if (newBook.getName() != null) {
            old.setName(newBook.getName());
        }
        if (newBook.getIsbn() != null) {
            old.setIsbn(newBook.getIsbn());
        }
        if (newBook.getAuthors() != null && newBook.getAuthors().size() > 0) {
            old.setAuthors(newBook.getAuthors());
        }
        if (newBook.getCountry() != null) {
            old.setCountry(newBook.getCountry());
        }
        if (newBook.getNumberOfPages() != null) {
            old.setNumberOfPages(newBook.getNumberOfPages());
        }
        if (newBook.getPublisher() != null) {
            old.setPublisher(newBook.getPublisher());
        }
        if (newBook.getReleaseDate() != null) {
            old.setReleaseDate(newBook.getReleaseDate());
        }

        return repository.save(old);
    }

    @Override
    public Book delete(Long id) {
        Book existing = read(id);
        repository.delete(existing);
        return existing;
    }
}
