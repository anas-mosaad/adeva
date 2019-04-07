package com.samples.api.books.internal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(
    name = "book",
        uniqueConstraints= {
            @UniqueConstraint(columnNames = "isbn", name = "isbn_unique"),
        }
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String isbn;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "book_authors",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") })
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Author> authors = new HashSet<>();

    private Integer numberOfPages;

    private String publisher;

    private String country;

    private LocalDate releaseDate;

    public void addAuthor(Author author) {
        authors.add(author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(numberOfPages, book.numberOfPages) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(country, book.country) &&
                Objects.equals(releaseDate, book.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
