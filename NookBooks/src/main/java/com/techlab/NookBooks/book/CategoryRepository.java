package com.techlab.NookBooks.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category,Long> {
    //querys personalizadas

    // TODO: query methods: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    List<Category> findByCategoryNameContaining(String category);
}
