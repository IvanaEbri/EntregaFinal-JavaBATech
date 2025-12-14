package com.techlab.NookBooks.controller;

import com.techlab.NookBooks.model.entity.OrderLine;
import com.techlab.NookBooks.model.entity.PurchaseOrder;
import com.techlab.NookBooks.model.dto.OrderLineDTO;
import com.techlab.NookBooks.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createOrder (@RequestBody PurchaseOrder purchaseOrder){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.createOrder(purchaseOrder));
    }

    @PostMapping("/line")
    public ResponseEntity<OrderLine> createOrderLine(@RequestBody OrderLineDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.createOrderLine(dto));

    }

    // GET/order?client=234
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> showOrders(@RequestParam(required = false, defaultValue = "") Long client){
        return ResponseEntity.status(HttpStatus.OK).body(this.orderService.showOrders(client));
    }

    // GET /order/{orderId}/lines
    @GetMapping("/{orderId}/lines")
    public ResponseEntity<List<OrderLine>> showOrderLine (@PathVariable Long orderId){
        return ResponseEntity.status(HttpStatus.OK).body(this.orderService.showOrden(orderId));

    }

    // PUT /order/{id}/confirm?client=36
    @PutMapping("/{id}/confirm")
    public ResponseEntity<PurchaseOrder> confirmOrder(
            @PathVariable Long id,
            @RequestParam Long client
    ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.confirmOrder(id, client));

    }

    // PUT /order/{id}/send
    @PutMapping("/{id}/send")
    public ResponseEntity<PurchaseOrder> sendOrder(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.sendOrder(id));
    }

    // PUT /order/{id}/deliver
    @PutMapping("/{id}/deliver")
    public ResponseEntity<PurchaseOrder> deliverOrder(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.deliverOrder(id));

    }

    // DELETE /order/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<PurchaseOrder> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.deleteOrder(id));
    }

    // DELETE /order/line/{id}
    @DeleteMapping("/line/{id}")
    public ResponseEntity<OrderLine> deleteOrderLine(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.deleteOrderLine(id));
    }
}
