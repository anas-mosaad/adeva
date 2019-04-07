package com.samples.api.books.internal.service;

import com.samples.api.books.internal.exceptions.ResourceNotFoundException;
import com.samples.api.books.internal.model.Book;
import com.samples.api.books.internal.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public List<Book> read(String name, String country, String publisher, Integer year) {
        if (name == null && country == null && publisher == null && year == null) {
            return repository.findAll();
        } else if (year == null) {
            Book book = new Book();
            book.setName(name);
            book.setCountry(country);
            book.setPublisher(publisher);
            return repository.findAll(Example.of(book, ExampleMatcher.matchingAll()));
        } else {
            LocalDate from = LocalDate.of(year, 1, 1);
            LocalDate to = LocalDate.of(year, 12, 31);
            if (name != null) {
                if (publisher != null) {
                    if (country != null) {
                        return repository.findAllByReleaseDateBetweenAndNameAndPublisherAndCountry(from, to, name, publisher, country);
                    }
                    return repository.findAllByReleaseDateBetweenAndNameAndPublisher(from, to, name, publisher);
                }
                if (country != null) {
                    return repository.findAllByReleaseDateBetweenAndNameAndCountry(from, to, name, country);
                }
                return repository.findAllByReleaseDateBetweenAndName(from, to, name);
            }
            if (country != null) {
                if (publisher != null) {
                    return repository.findAllByReleaseDateBetweenAndPublisherAndCountry(from, to, publisher, country);
                }
                return repository.findAllByReleaseDateBetweenAndCountry(from, to, country);
            }
            if (publisher != null) {
                return repository.findAllByReleaseDateBetweenAndPublisher(from, to, publisher);
            }
            return repository.findAllByReleaseDateBetween(from, to);
        }
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
