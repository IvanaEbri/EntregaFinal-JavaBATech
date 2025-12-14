package com.techlab.NookBooks.controller;

import com.techlab.NookBooks.service.BookService;
import com.techlab.NookBooks.model.entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook (@RequestBody Book book){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookService.createBook(book));
    }

    // GET /book?search=valor   o   /book?category=5
    @GetMapping
    public ResponseEntity<List<Book>> showBooks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long category) {

        if (category != null) {
            return ResponseEntity.status(HttpStatus.OK).body(this.bookService.showBookByCategory(category));
        }
        // si no se envió search, se usa cadena vacía
        return ResponseEntity.status(HttpStatus.OK).body(this.bookService.showBook(search != null ? search : ""));
    }

    @GetMapping("{bookId}")
    public ResponseEntity<Book> showBook (@PathVariable Long bookId){
        return ResponseEntity.status(HttpStatus.OK).body(this.bookService.searchBook(bookId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.bookService.editBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook (@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.bookService.deleteBook(id));

    }
}
