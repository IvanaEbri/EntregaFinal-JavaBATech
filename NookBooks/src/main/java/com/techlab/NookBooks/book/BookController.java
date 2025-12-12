package com.techlab.NookBooks.book;

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
    public Book createBook (@RequestBody Book book){
        return this.bookService.createBook(book);
    }

    // GET /book?search=valor   o   /book?category=5
    @GetMapping
    public List<Book> showBooks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long category) {

        if (category != null) {
            return this.bookService.showBookByCategory(category);
        }
        // si no se envió search, se usa cadena vacía
        return this.bookService.showBook(search != null ? search : "");
    }

    @GetMapping("{bookId}")
    public Book showBook (@PathVariable Long bookId){ return (Book) this.bookService.searchBook(bookId);}


    @PutMapping("/{id}")
    public Book editBook(@PathVariable Long id, @RequestBody Book book) {
        return this.bookService.editBook(id, book);
    }

    @DeleteMapping("/{id}")
    public Book deleteBook (@PathVariable Long id){
        return this.bookService.deleteBook(id);
    }
}
