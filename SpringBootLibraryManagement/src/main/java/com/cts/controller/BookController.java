package com.cts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.exception.ResourceNotFoundException;
import com.cts.model.Book;
import com.cts.repository.BookRepository;

@RestController
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping("/")
	public List<Book> getLoginPage() {
		return bookRepository.findAll();
	}


	@GetMapping("/book")
	public List<Book> getAllBook() {
		return bookRepository.findAll();
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Integer bookId)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + bookId));
		return ResponseEntity.ok().body(book);
	}

	@PostMapping("/books")
	public Book createBooks(@Valid @RequestBody Book book) {
		return bookRepository.save(book);
	}

	@PutMapping("/book/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Integer bookId,
			@Valid @RequestBody Book bookDetails) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + bookId));

		book.setBookName(bookDetails.getBookName());
		book.setAuthorName(bookDetails.getAuthorName());
		book.setNoOfCopies(bookDetails.getNoOfCopies());
		final Book updatedBook = bookRepository.save(book);
		return ResponseEntity.ok(updatedBook);
	}

	@DeleteMapping("/book/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Integer bookId)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + bookId));

		bookRepository.delete(book);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
