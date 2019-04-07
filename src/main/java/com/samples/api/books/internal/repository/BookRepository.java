package com.samples.api.books.internal.repository;

import com.samples.api.books.internal.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateBetween(LocalDate from, LocalDate to);
    List<Book> findAllByReleaseDateBetweenAndName(LocalDate from, LocalDate to, String name);
    List<Book> findAllByReleaseDateBetweenAndCountry(LocalDate from, LocalDate to, String country);
    List<Book> findAllByReleaseDateBetweenAndPublisher(LocalDate from, LocalDate to, String publisher);
    List<Book> findAllByReleaseDateBetweenAndNameAndPublisher(LocalDate from, LocalDate to, String name, String publisher);
    List<Book> findAllByReleaseDateBetweenAndNameAndCountry(LocalDate from, LocalDate to, String name, String country);
    List<Book> findAllByReleaseDateBetweenAndPublisherAndCountry(LocalDate from, LocalDate to, String publisher, String country);
    List<Book> findAllByReleaseDateBetweenAndNameAndPublisherAndCountry(LocalDate from, LocalDate to, String name, String publisher, String country);
}
