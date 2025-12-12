package com.techlab.NookBooks.book;

import com.techlab.NookBooks.client.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;
    private CategoryService categoryService;

    public BookService(BookRepository repository, CategoryService categoryService) {

        this.bookRepository = repository;
        this.categoryService = categoryService;
    }

    public Book createBook (Book book){
        //TODO: verificar campos
        if (book.getTitle() == null || book.getAuthor() == null || book.getDescription() == null || book.getUrlImage() == null){
            System.out.println("Debe completar todos los datos.");
            return null;
        }
        if (book.getCategory() == null || book.getCategory().getId() == null){
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

        // Buscar categoría gestionada por JPA
        Category category = categoryService.searchCategory(
                book.getCategory().getId()
        );
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

    public Book searchBook (Long id){
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el libro"));
    }

    public List<Book> showBookByCategory (Long category){
        if (category != null){
            return this.bookRepository.findByActiveTrueAndCategory_Id(category);
        }
        return this.bookRepository.findAll();
    }

    public Book editBook(Long id, Book dataBook) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el libro."));

        // Validaciones
        if (isNullOrEmpty(dataBook.getTitle()) ||
                isNullOrEmpty(dataBook.getAuthor()) ||
                isNullOrEmpty(dataBook.getDescription()) ||
                isNullOrEmpty(dataBook.getUrlImage())) {

            throw new IllegalArgumentException("Todos los campos de texto son obligatorios");
        }

        if (dataBook.getCategory() == null || dataBook.getCategory().getId() == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        if (dataBook.getPrice() == null || dataBook.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (dataBook.getStock() == null || dataBook.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        // Buscar categoría gestionada por JPA
        Category category = categoryService.searchCategory(
                dataBook.getCategory().getId()
        );

        // Actualizar campos
        book.setTitle(dataBook.getTitle());
        book.setAuthor(dataBook.getAuthor());
        book.setDescription(dataBook.getDescription());
        book.setCategory(category);
        book.setUrlImage(dataBook.getUrlImage());
        book.setPrice(dataBook.getPrice());
        book.setStock(dataBook.getStock());

        return bookRepository.save(book);
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
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
