package com.samples.api.books.rest;

import com.samples.api.books.dto.Book;
import com.samples.api.books.external.service.AceAndFireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/external-books")
public class ExternalBookController {

    private final AceAndFireService service;

    public ExternalBookController(AceAndFireService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = GET)
    public BookResponse getAllBooks(@RequestParam(required = false) String name) {
        List<Book> books = name == null
                ? service.getAllBooks()
                : service.getAllBooksByName(name);
        return new BookResponse(HttpStatus.OK.value(), "success", books);
    }
}
