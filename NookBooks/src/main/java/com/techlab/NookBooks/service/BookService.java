package com.techlab.NookBooks.service;

import com.techlab.NookBooks.exception.CheckedDataException;
import com.techlab.NookBooks.exception.InsufficientStockException;
import com.techlab.NookBooks.exception.NotFoundException;
import com.techlab.NookBooks.exception.NullException;
import com.techlab.NookBooks.model.entity.Book;
import com.techlab.NookBooks.model.entity.Category;
import com.techlab.NookBooks.repository.BookRepository;
import org.aspectj.weaver.ast.Not;
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

    public  Book createBook (Book book) throws NullException, NotFoundException, CheckedDataException {

        if (isNullOrEmpty(book.getTitle()) || isNullOrEmpty(book.getAuthor()) || isNullOrEmpty(book.getDescription()) || isNullOrEmpty(book.getUrlImage())) {
            throw new NullException("Debe completar todos los datos.");
        }
        if (book.getCategory() == null || book.getCategory().getId() == null) {
            throw new NullException("Debe seleccionar la categoria del libro.");
        }
        if (book.getPrice() <= 0.00) {
            throw new CheckedDataException("El precio debe ser un valor mayor a $0,00.");
        }
        if (book.getStock() < 0) {
            throw new CheckedDataException("El stock debe ser una cantidad valida.");
        }

        // Buscar categoría gestionada por JPA
        Category category = checkCategory(book);

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
                .orElseThrow(() -> new NotFoundException("No se encontró el libro"));
    }

    public List<Book> showBookByCategory (Long category){
        if (category != null){
            return this.bookRepository.findByActiveTrueAndCategory_Id(category);
        }
        return this.bookRepository.findAll();
    }

    public Book editBook(Long id, Book dataBook) throws CheckedDataException, NotFoundException , NullException{

        Book book = searchBook(id);

        // Validaciones
        if (isNullOrEmpty(dataBook.getTitle()) ||
                isNullOrEmpty(dataBook.getAuthor()) ||
                isNullOrEmpty(dataBook.getDescription()) ||
                isNullOrEmpty(dataBook.getUrlImage())) {

            throw new NullException("Todos los campos de texto son obligatorios");
        }

        if (dataBook.getCategory() == null || dataBook.getCategory().getId() == null) {
            throw new NullException("La categoría es obligatoria");
        }

        if (dataBook.getPrice() == null || dataBook.getPrice() <= 0) {
            throw new CheckedDataException("El precio debe ser mayor a 0");
        }

        if (dataBook.getStock() == null || dataBook.getStock() < 0) {
            throw new CheckedDataException("El stock no puede ser negativo");
        }

        // Buscar categoría gestionada por JPA
        Category category = checkCategory(book);

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

    private Category checkCategory(Book book) throws NotFoundException{
        Category category = categoryService.searchCategory(
                book.getCategory().getId());
        if (category.getCategoryName().isBlank()){
            throw new NotFoundException("No se encontró la categoria.");}
        return category;
    }

    public Book deleteBook (Long id) throws NotFoundException{
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new NotFoundException("No se puede borrar el libro. No se encontró el mismo");
        }

        Book book = bookOptional.get();
        book.setActive(false);
        return this.bookRepository.save(book);
    }

    public Book removeStock (Long id, Integer quantity) throws NotFoundException, InsufficientStockException  {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new NotFoundException("No se encuentra el libro. No se podrá modificar el stock");
        }

        Book book = bookOptional.get();

        if (book.getStock()<quantity){
            throw new InsufficientStockException("No hay stock suficiente para el pedido.");
        }
        book.setStock(book.getStock()-quantity);
        return this.bookRepository.save(book);
    }

    public Book addStock (Long id, Integer quantity) throws     NotFoundException{
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new NotFoundException("No se encuentra el libro. No se podrá modificar el stock");
        }

        Book book = bookOptional.get();
        book.setStock(book.getStock()+quantity);

        return this.bookRepository.save(book);
    }

}
