package com.mimecast.books.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Book {

    @JsonProperty("book_title")
    private String name;

    private String summary;
}
