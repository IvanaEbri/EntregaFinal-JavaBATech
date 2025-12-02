package com.techlab.book;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository repository) {

        this.bookRepository = repository;
    }

}
