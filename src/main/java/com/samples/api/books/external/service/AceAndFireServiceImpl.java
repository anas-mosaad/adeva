package com.samples.api.books.external.service;

import com.samples.api.books.external.dto.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class AceAndFireServiceImpl implements AceAndFireService {

    private static final String ALL_BOOKS_URL = "https://www.anapioficeandfire.com/api/books";
    private static final String BOOKS_BY_NAME_URL = ALL_BOOKS_URL + "?name={name}";
    private final RestTemplate template;

    public AceAndFireServiceImpl(RestTemplate template) {
        this.template = template;
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("Fetching all books from Ace and Fire.");
        return template.exchange(ALL_BOOKS_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {}).getBody();
    }

    @Override
    public List<Book> getAllBooksByName(String name) {
        log.info("Fetching book with name {} from Ace and Fire.", name);
        return template.exchange(BOOKS_BY_NAME_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {}, name).getBody();
    }
}
