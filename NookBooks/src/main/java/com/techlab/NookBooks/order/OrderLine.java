package com.techlab.NookBooks.order;

import com.techlab.NookBooks.book.Book;
import jakarta.persistence.*;


@Entity

public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;
    private Integer quantity;
    private Double valueLine;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    public OrderLine(Book book, Integer quantity, PurchaseOrder purchaseOrder) {
        this.book = book;
        this.quantity = quantity;
        this.purchaseOrder = purchaseOrder;
        this.valueLine = book.getPrice()*quantity;
    }

    public OrderLine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getValueLine() {
        return valueLine;
    }

    public void setValueLine(Double valueLine) {
        this.valueLine = valueLine;
    }

    public PurchaseOrder getOrder() {
        return purchaseOrder;
    }

    public void setOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
