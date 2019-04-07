package com.samples.api.books.internal.service;

import com.samples.api.books.BooksApplication;
import com.samples.api.books.internal.dto.Book;
import com.samples.api.books.rest.BookResponse;
import com.samples.api.books.rest.BookResponseWithMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

import static com.samples.api.books.rest.InternalBookController.DELETE;
import static com.samples.api.books.rest.InternalBookController.UPDATED;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookServiceTest {

    @LocalServerPort
    private int port;

    private String booksUri;
//    private String booksUri = "http://localhost:8080/api/v1/books";

    private RestTemplate template = getRestTemplate();
    private Book book;
    private List<Book> books = new ArrayList<>();

    private static int getInt(int max, int min) {
        return Math.max((int) (Math.random() * max), min);
    }

    @Before
    public void setUp() {
        booksUri = "http://localhost:" + port + "/api/v1/books";
        System.out.println("URL: " + booksUri);
        book = create(createBook());
    }

    @After
    public void tearDown() {
        if (book != null) {
            template.delete(booksUri + "/" + book.getId());
        }
        for(Book book: books) {
            template.delete(booksUri + "/" + book.getId());
        }
    }

    @Test
    public void whenCreateThenShouldSetId() {
        Book book2 = create(createBook());
        books.add(book2);
    }

    @Test
    public void whenReadThenShouldGetMatchingBook() {
        ResponseEntity<BookResponse<List<Book>>> bookResponse = template.exchange(booksUri + "/" + book.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BookResponse<List<Book>>>(){});

        // Assert
        Book book2 = getBook(bookResponse);
        assertThat(book2).isNotNull();
        assertThat(book2.getId()).isEqualTo(book.getId());
        assertThat(book2.getName()).isEqualTo(book.getName());
        assertThat(book2.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(book2.getPublisher()).isEqualTo(book.getPublisher());
        assertThat(book2.getCountry()).isEqualTo(book.getCountry());
        assertThat(book2.getNumberOfPages()).isEqualTo(book.getNumberOfPages());
        assertThat(book2.getAuthors()).containsExactlyInAnyOrderElementsOf(book.getAuthors());
    }

    @Test
    public void whenReadAllBokksThenShouldReturnListWithAtLeastOneBook() {
        // Act
        ResponseEntity<BookResponse<List<Book>>> bookResponse = template.exchange(booksUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BookResponse<List<Book>>>(){});

        // Assert
        BookResponse<List<Book>> body = bookResponse.getBody();
        assertThat(body).isNotNull();
        List<Book> data = body.getData();
        assertThat(data).isNotNull();
        assertThat(data.size()).isGreaterThan(0);
    }

    @Test
    public void whenUpdateNameThenShouldGetBookWithUpdatedName() {
        // Arrange
        Book input = new Book();
        String newName = "Some new name";
        input.setName(newName);

        // Acr
        Book book2 = update(input);

        // Assert
        assertThat(book2).isNotNull();
        assertThat(book2.getName()).isEqualTo(newName);
    }

    @Test
    public void whenUpdateISBNThenShouldGetBookWithUpdatedISBN() {
        // Arrange
        Book input = new Book();
        String newIsbn = "987632845-009";
        input.setIsbn(newIsbn);

        // Acr
        Book book2 = update(input);

        // Assert
        assertThat(book2.getIsbn()).isEqualTo(newIsbn);
    }

    @Test
    public void whenUpdateCountryThenShouldGetBookWithUpdatedCountry() {
        // Arrange
        Book input = new Book();
        input.setCountry("USA");

        // Acr
        Book book2 = update(input);

        // Assert
        assertThat(book2.getCountry()).isEqualTo(input.getCountry());
    }

    @Test
    public void whenUpdatePublisherThenShouldGetBookWithUpdatedPublisher() {
        // Arrange
        Book input = new Book();
        input.setPublisher("Manning");

        // Acr
        Book book2 = update(input);

        // Assert
        assertThat(book2.getPublisher()).isEqualTo(input.getPublisher());
    }

    @Test
    public void whenUpdateReleaseDateThenShouldGetBookWithUpdatedReleaseDate() {
        // Arrange
        Book input = new Book();
        input.setReleaseDate(LocalDate.now());

        // Acr
        Book book2 = update(input);

        // Assert
        assertThat(book2.getReleaseDate()).isEqualTo(input.getReleaseDate());
    }

    @Test
    public void whenUpdatePagesThenShouldGetBookWithUpdatedPages() {
        // Arrange
        Book input = new Book();
        input.setNumberOfPages(550);

        // Acr
        Book book2 = update(input);

        // Assert
        assertThat(book2.getNumberOfPages()).isEqualTo(input.getNumberOfPages());
    }

    @Test
    public void whenAuthorsThenShouldGetBookWithUpdatedAuthors() {
        // Arrange
        Book input = new Book();
        Set<String> authors = new HashSet<>();
        authors.add("Sam");
        authors.add("Martin");
        input.setAuthors(authors);

        // Acr
        Book book2 = update(input);

        // Assert
        assertThat(book2.getAuthors()).isEqualTo(authors);
    }

    @Test
    public void whenDeleteThenShouldRemove() {
        ResponseEntity<BookResponseWithMessage<Void>> bookResponse = template.exchange(booksUri + "/" + book.getId(),
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<BookResponseWithMessage<Void>>() {});

        BookResponseWithMessage<Void> body = bookResponse.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getStatus()).isEqualTo("success");
        assertThat(body.getMessage()).isEqualTo(String.format(DELETE, book.getName()));
        book = null;
    }

    private static int suffix = 1;

    private Book createBook() {
        Book book = new Book();
        book.setName("Sample Book name" + suffix);
        book.setIsbn("12454386-870" + suffix++);
        book.setPublisher("IBM Press");
        book.setCountry("Egypt");
        book.setReleaseDate(LocalDate.of(getInt(2019, 2004), 11, 1));
        book.setNumberOfPages(getInt(1000, 250));
        book.setAuthors(getAuthors(getInt(6, 5)));

        return book;
    }

    private Set<String> getAuthors(int count) {
        Set<String> authors = new HashSet<>();
        for (int i = 0; i < count; i++) {
            authors.add("Author - " + i);
        }
        return authors;
    }

    private RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate template = new RestTemplate(requestFactory);

        template.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        return template;
    }
    private Book create(Book input) {
        ResponseEntity<BookResponse<List<Book>>> bookResponse = template.exchange(booksUri,
                HttpMethod.POST,
                new HttpEntity<>(input),
                new ParameterizedTypeReference<BookResponse<List<Book>>>(){});

        // Assert
        Book book2 = getBook(bookResponse);
        assertThat(book2).isNotNull();
        assertThat(book2.getId()).isNotNull();
        return book2;
    }

    private Book getBook(ResponseEntity<BookResponse<List<Book>>> bookResponse) {
        BookResponse<List<Book>> body = bookResponse.getBody();
        assertThat(body).isNotNull();
        List<Book> data = body.getData();
        assertThat(data).isNotNull();
        assertThat(data).hasSize(1);
        return data.get(0);
    }

    private Book update(Book input) {
        String name = book.getName();
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "merge-patch+json");
        headers.setContentType(mediaType);

        HttpEntity<Book> entity = new HttpEntity<>(input, headers);

        ResponseEntity<BookResponseWithMessage<Book>> bookResponse = template.exchange(booksUri + "/" + book.getId(),
                HttpMethod.PATCH,
                entity,
                new ParameterizedTypeReference<BookResponseWithMessage<Book>>() {});

        BookResponseWithMessage<Book> body = bookResponse.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getStatus()).isEqualTo("success");
        assertThat(body.getMessage()).isEqualTo(String.format(UPDATED, name));
        Book data = body.getData();
        assertThat(data).isNotNull();
        book = data; // to reflect the new name - if name was updated.
        return data;
    }
}