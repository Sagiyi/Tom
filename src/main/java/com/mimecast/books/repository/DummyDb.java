package com.mimecast.books.repository;

import com.mimecast.books.entities.Author;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class DummyDb {
    private Map<String /*AuthorName*/, Author> db = new HashMap<>();
}
