package com.techlab.book;

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

    // GET /book?book="valor_busqueda"
    @GetMapping
    public List<Book> showBooks(
            @RequestParam(required = false, defaultValue = "") String searchValue){
        return this.bookService.showBook(searchValue);
    }

    // GET /book?category=id
    @GetMapping
    public List<Book> showBooks( @RequestParam(required = true, defaultValue = "") Long category){
        return this.bookService.showBookByCategory(category);
    }

    @PutMapping("/{id}")
    public Book editBook(@PathVariable Long id, @RequestBody Book book) {
        return this.bookService.editBook(id, book);
    }

    @DeleteMapping("/{id}")
    public Book deleteBook (@PathVariable Long id){
        return this.bookService.deleteBook(id);
    }
}
