package com.techlab.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String description;

    @ManyToOne
    private Category category;
    private Double price;
    private String urlImage;
    private Integer stock;
    private Boolean active;

    public Book(String title, String author, String description, Category category, Double price, String urlImage, Integer stock) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.category = category;
        this.price = price;
        this.urlImage = urlImage;
        this.stock = stock;
        this.active = Boolean.TRUE;
    }
}
