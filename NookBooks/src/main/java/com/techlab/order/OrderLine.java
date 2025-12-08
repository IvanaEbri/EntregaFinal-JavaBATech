package com.techlab.order;

import com.techlab.book.Book;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;
    private Integer quantity;
    private Double valueLine;

    @ManyToOne
    private Order order;

    public OrderLine(Book book, Integer quantity, Order order) {
        this.book = book;
        this.quantity = quantity;
        this.order = order;
        this.valueLine = book.getPrice()*quantity;
    }
}
