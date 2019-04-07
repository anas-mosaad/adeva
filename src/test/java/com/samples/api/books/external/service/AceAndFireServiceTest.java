package com.samples.api.books.external.service;

import com.samples.api.books.external.dto.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AceAndFireServiceTest {
    @Autowired private AceAndFireService service;

    @Test
    public void givenDefaultsWhenGetAllBooksThenShouldReturnSomeBooks() {
        // Act
        List<Book> books = service.getAllBooks();

        // Assert
        assertThat(books).isNotNull();
        assertThat(books).hasAtLeastOneElementOfType(Book.class);
    }

    @Test
    public void givenAnExistingBookWhenGetAllBooksByNameThenShouldReturnIt() {
        // Act
        List<Book> books = service.getAllBooksByName("A Game of Thrones");

        // Assert
        assertThat(books).isNotNull();
        assertThat(books).hasAtLeastOneElementOfType(Book.class);
    }

    @Test
    public void givenNonExistingBookWhenGetAllBooksByNameThenShouldReturnEmpty() {
        // Act
        List<Book> books = service.getAllBooksByName("Non existent book!");

        // Assert
        assertThat(books).isNotNull();
        assertThat(books).hasSize(0);
    }
}