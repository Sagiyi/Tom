package com.mimecast.books;

import com.mimecast.books.entities.Author;
import com.mimecast.books.rest.AuthorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
class BooksApplicationTests {

	@Autowired
	private AuthorController authorController;

	@Test
	public void testUrl(){
		authorController.changeUrl("https://api.nytimes.com/svc/books/v3/","reviews.json");
		authorController.createAuthor("Stephen King", "Ea6RpJTEggFAPG6mF9cc31vRKaZkm97S");
		final Author stephen_king = authorController.getAuthor("Stephen King");
		System.out.println("Final: " + stephen_king);
		assertNotNull(stephen_king);
		assertNotNull(stephen_king.getBooks());
		assertTrue(stephen_king.getBooks().stream().anyMatch(book -> "It".equals(book.getName())));
	}

	@Test
	void contextLoads() {
	}

}
