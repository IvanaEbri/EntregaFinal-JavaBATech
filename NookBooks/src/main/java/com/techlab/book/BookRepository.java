package com.techlab.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    //querys personalizadas

    // TODO: query methods: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthorContaining (String author);
    List<Book> findByCategory (Category category);

    // List<Product> findById (Integer id);
}
