package com.mimecast.books.service;

import com.mimecast.books.entities.Author;
import com.mimecast.books.entities.Book;
import com.mimecast.books.entities.BooksReviewResponse;
import com.mimecast.books.repository.DummyDb;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AuthorService {

    private RestTemplate restTemplate = new RestTemplate();//or get from Spring as a bean... (I spared time here). or use WebClient..

    @Setter
    @Value("${url.books:https://api.nytimes.com/svc/books/v3/}")
    private String baseUrl;

    @Setter
    @Value("${url.relative.books.review:reviews.json}")
    private String relativeUrl;

    private DummyDb db;

    public AuthorService(DummyDb db) {
        this.db = db;
    }

    public List<Book> getAuthorDataFromExternalProvider(String authorName, String apiKey){
        //handling SSL issue - not a production code.
        try {
            SSLUtil.turnOffSslChecking();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + relativeUrl)
                .queryParam("author", authorName)
                .queryParam("api-key", apiKey);
        final URI uri = builder.buildAndExpand().toUri();
        log.debug("url is {}", uri);

        ResponseEntity<BooksReviewResponse> result = restTemplate.exchange(uri, HttpMethod.GET, null, BooksReviewResponse.class);
        log.debug("result: {}", String.valueOf(result.getBody()));
        return result.getBody().getResults();
    }

    public void persist(Author author) {
        db.getDb().put(author.getName(), author);
    }

    public Author getAuthor(String name) {
        return db.getDb().get(name);
    }
}
