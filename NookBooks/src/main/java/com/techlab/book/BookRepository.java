package com.techlab.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    //querys personalizadas

    // TODO: query methods: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    List<Book> findByTitleContainingAndActiveTrue(String title);

    List<Book> findByAuthorContainingAndActiveTrue (String author);

    // Busca libros donde el campo 'category' tenga un 'categoryName' específico
    List<Book> findByActiveTrueAndCategory_CategoryName(String categoryName);

    List<Book> findByActiveTrueAndCategory_CategoryId(Long categoryId);

    List<Book> findByActiveTrue ();

    // List<Book> findById (Integer id);
}
