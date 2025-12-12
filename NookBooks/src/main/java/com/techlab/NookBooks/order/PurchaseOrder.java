package com.techlab.NookBooks.order;

import com.techlab.NookBooks.client.Client;
import jakarta.persistence.*;

@Entity

public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;
    private Double totalPrice;
    private Integer state;  // TODO: 0-pendiente, 1-confirmado, 2-enviado, 3-entregado, 4-cancelado

    public PurchaseOrder() {
        //this.client = client; pendiente sin asignar cliente, al asignarlo se confirmará
        this.totalPrice = Double.valueOf(0);
        this.state = 0;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
