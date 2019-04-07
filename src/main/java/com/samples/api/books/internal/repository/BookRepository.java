package com.samples.api.books.internal.repository;

import com.samples.api.books.internal.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
