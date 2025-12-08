package com.techlab.book;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository repository) {

        this.bookRepository = repository;
    }

    public Book createBook (Book book){
        //TODO: verificar campos
        if (book.getTitle() == null || book.getAuthor() == null || book.getDescription() == null || book.getUrlImage() == null){
            System.out.println("Debe completar todos los datos.");
            return null;
        }
        if (book.getCategory() == null || book.getCategory() instanceof Category){
            System.out.println("Debe seleccionar la categoria del libro.");
            return null;
        }
        if (book.getPrice() <= 0.00){
            System.out.println("El precio debe ser un valor mayor a $0,00.");
            return null;
        }
        if (book.getStock() < 0){
            System.out.println("El stock debe ser una cantidad valida.");
            return null;
        }
        System.out.println("Creando un nuevo producto");
        return this.bookRepository.save(book);
    }
    public List<Book> showBook (String book){
        if (!book.isEmpty()){
            List<Book> booksByCategory = this.bookRepository.findByActiveTrueAndCategory_CategoryName(book);
            List<Book> booksByTitle = this.bookRepository.findByTitleContainingAndActiveTrue(book);
            List<Book> booksByAuthor = this.bookRepository.findByAuthorContainingAndActiveTrue(book);

            List<Book> combinedList = new ArrayList<>(booksByTitle);
            combinedList.addAll(booksByAuthor);
            combinedList.addAll(booksByCategory);
            List<Book> uniqueBooks = combinedList.stream().distinct().collect(Collectors.toList());
            return uniqueBooks;
        }
        return this.bookRepository.findByActiveTrue();
    }

    public Optional<Book> showBook (Long bookId){
        Optional<Book> bookOptional = this.bookRepository.findById(bookId);
        if (!bookOptional.isEmpty()) {
            return bookOptional;
        }
        return null;
    }

    public List<Book> showBookByCategory (Long category){
        if (category != null){
            return this.bookRepository.findByActiveTrueAndCategory_CategoryId(category);
        }
        return this.bookRepository.findAll();
    }

    public Book editBook (Long id, Book dataBook){
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el libro."));

        if (dataBook.getTitle() == null || book.getAuthor() == null || dataBook.getDescription() == null || dataBook.getUrlImage() == null){
            System.out.println("No se puede editar el libro. Debe completar todos los datos.");
            return null;
        }
        if (book.getCategory() == null || book.getCategory() instanceof Category){
            System.out.println("No se puede editar el libro. Debe seleccionar la categoria del libro.");
            return null;
        }
        if (dataBook.getPrice() <= 0.00){
            System.out.println("No se puede editar el libro. El precio debe ser un valor mayor a $0,00.");
            return null;
        }
        if (dataBook.getStock() < 0){
            System.out.println("No se puede editar el libro. El stock debe ser una cantidad valida.");
            return null;
        }
        book.setTitle(dataBook.getTitle());
        book.setAuthor(dataBook.getAuthor());
        book.setDescription(dataBook.getDescription());
        book.setCategory(dataBook.getCategory());
        book.setUrlImage(dataBook.getUrlImage());
        book.setPrice(dataBook.getPrice());
        book.setStock(dataBook.getStock());
        this.bookRepository.save(book);

        return book;
    }
    public Book deleteBook (Long id){
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            System.out.println("No se puede borrar el libro. No se encontró el mismo");
            return null;
        }

        Book book = bookOptional.get();
        book.setActive(false);

        this.bookRepository.save(book);

        System.out.println("Se borro correctamente el libro: " + book.getTitle() + " - " + book.getAuthor());
        return book;
    }

    public Book removeStock (Long id, Integer quantity){
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            System.out.println("No se encuentra el libro. No se podrá modificar el stock");
            return null;
        }

        Book book = bookOptional.get();
        book.setStock(book.getStock()-quantity);

        this.bookRepository.save(book);

        System.out.println("Se modificó correctamente el stock del libro: " + book.getTitle() + " - " + book.getAuthor()+ ", stock actual: " +book.getStock());
        return book;
    }

    public Book addStock (Long id, Integer quantity){
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            System.out.println("No se encuentra el libro. No se podrá modificar el stock");
            return null;
        }

        Book book = bookOptional.get();
        book.setStock(book.getStock()+quantity);

        this.bookRepository.save(book);

        System.out.println("Se modificó correctamente el stock del libro: " + book.getTitle() + " - " + book.getAuthor()+ ", stock actual: " +book.getStock());
        return book;
    }

}
