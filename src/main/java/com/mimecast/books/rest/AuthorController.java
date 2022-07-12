package com.mimecast.books.rest;

import com.mimecast.books.entities.Author;
import com.mimecast.books.entities.Book;
import com.mimecast.books.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Void> createAuthor(String name, String apiKey) {
        final List<Book> books = authorService.getAuthorDataFromExternalProvider(name, apiKey);
        Author author = new Author(name, books);
        authorService.persist(author);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Author getAuthor(String name){
        return authorService.getAuthor(name);
    }

    //without this we can change it in properties file, but it  requires to restart the service to load SpringBoot context
    //it may move to other controller.
    @PutMapping("/changeUrl")
    public ResponseEntity<Void> changeUrl(String baseUrl, String relativeUrl) {
        authorService.setBaseUrl(baseUrl);
        authorService.setRelativeUrl(relativeUrl);
        return ResponseEntity.noContent().build();
    }
}

