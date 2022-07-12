package com.mimecast.books.entities;

import lombok.Data;

import java.util.List;

@Data
public class BooksReviewResponse  {
    private List<Book> results;
}
