package com.samples.api.books.rest;

import com.samples.api.books.external.dto.Book;
import com.samples.api.books.external.service.AceAndFireService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.samples.api.books.rest.BookResponse.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/external-books")
public class ExternalBookController {

    private final AceAndFireService service;

    public ExternalBookController(AceAndFireService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = GET)
    public BookResponse<List<Book>> getAllBooks(@RequestParam(required = false) String name) {
        List<Book> books = name == null
                ? service.getAllBooks()
                : service.getAllBooksByName(name);
        return ok(books);
    }
}
