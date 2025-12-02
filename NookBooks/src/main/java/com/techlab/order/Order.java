package com.techlab.order;

import com.techlab.client.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;
    private Double totalPrice;
    private Integer state;  // TODO: 0-pendiente, 1-confirmado, 2-enviado, 3-entregado, 4-cancelado

    public Order(Client client) {
        this.client = client;
        this.totalPrice = Double.valueOf(0);
        this.state = 0;
    }
}
