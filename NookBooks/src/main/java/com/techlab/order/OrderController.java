package com.techlab.order;

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
    public Order createOrder (@RequestBody Order order){
        return this.orderService.createOrder(order);
    }

    @PostMapping("/line")
    public OrderLine OrderLine (@RequestBody OrderLine orderLine){
        return this.orderService.createOrderLine(orderLine);
    }

    // GET/order?client=234
    @GetMapping
    public List<Order> showOrders(@RequestParam(required = false, defaultValue = "") Long client){
        return this.orderService.showOrders(client);
    }

    // GET /order/{orderId}/lines
    @GetMapping("/{orderId}/lines")
    public List<OrderLine> showOrderLine (@PathVariable Long orderId){
        return this.orderService.showOrden(orderId);
    }

    // PUT /order/{id}/confirm?client=36
    @PutMapping("/{id}/confirm")
    public Order confirmOrder(
            @PathVariable Long id,
            @RequestParam Long client
    ) {
        return orderService.confirmOrder(id, client);
    }

    // PUT /order/{id}/send
    @PutMapping("/{id}/send")
    public Order sendOrder(@PathVariable Long id) {
        return orderService.sendOrder(id);
    }

    // PUT /order/{id}/deliver
    @PutMapping("/{id}/deliver")
    public Order deliverOrder(@PathVariable Long id) {
        return orderService.deliverOrder(id);
    }

    // DELETE /order/{id}
    @DeleteMapping("/{id}")
    public Order deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    // DELETE /order/line/{id}
    @DeleteMapping("/line/{id}")
    public OrderLine deleteOrderLine(@PathVariable Long id) {
        return orderService.deleteOrderLine(id);
    }
}
