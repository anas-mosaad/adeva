package com.samples.api.books.rest;

import com.samples.api.books.internal.dto.Book;
import com.samples.api.books.internal.service.BookService;
import com.samples.api.books.internal.service.MapperService;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;

import static com.samples.api.books.rest.BookResponse.created;
import static com.samples.api.books.rest.BookResponse.ok;
import static com.samples.api.books.rest.BookResponseWithMessage.deleted;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Transactional
@RestController
@RequestMapping("/api/v1/books")
public class InternalBookController {

    public static final String UPDATED = "The book %s was updated successfully";
    public static final String DELETE = "The book %s was deleted successfully";

    private final BookService service;
    private final MapperService mapper;

    public InternalBookController(BookService service, MapperService mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @RequestMapping(value = "", method = GET)
    public BookResponse<Collection<Book>> getAllBooks() {
        return ok(mapper.toDTO(service.read()));
    }

    @RequestMapping(value = "", method = POST)
    public BookResponse<Collection<Book>> createBook(@RequestBody Book book) {
        return created(Collections.singletonList(mapper.toDTO(service.create(mapper.toModel(book)))));
    }

    @RequestMapping(value = "/{id}", method = GET)
    public BookResponse<Collection<Book>> get(@PathVariable Long id) {
        return ok(Collections.singletonList(mapper.toDTO(service.read(id))));
    }

    @RequestMapping(value = "/{id}", method = PATCH)
    public BookResponseWithMessage<Book> update(@PathVariable Long id, @RequestBody Book book) {
        com.samples.api.books.internal.model.Book old = service.read(id);
        String name = old.getName();
        return BookResponseWithMessage.ok(mapper.toDTO(service.update(old, mapper.toModel(book))), String.format(UPDATED, name));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BookResponseWithMessage<?> delete(@PathVariable Long id) {
        Book deleted = mapper.toDTO(service.delete(id));
        return deleted(String.format(DELETE, deleted.getName()));
    }
}
