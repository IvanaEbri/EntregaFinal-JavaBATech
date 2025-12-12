package com.techlab.NookBooks.order;

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
    public PurchaseOrder createOrder (@RequestBody PurchaseOrder purchaseOrder){
        return this.orderService.createOrder(purchaseOrder);
    }

    @PostMapping("/line")
    public OrderLine createOrderLine(@RequestBody OrderLineDTO dto){
        return this.orderService.createOrderLine(dto);
    }

    // GET/order?client=234
    @GetMapping
    public List<PurchaseOrder> showOrders(@RequestParam(required = false, defaultValue = "") Long client){
        return this.orderService.showOrders(client);
    }

    // GET /order/{orderId}/lines
    @GetMapping("/{orderId}/lines")
    public List<OrderLine> showOrderLine (@PathVariable Long orderId){
        return this.orderService.showOrden(orderId);
    }

    // PUT /order/{id}/confirm?client=36
    @PutMapping("/{id}/confirm")
    public PurchaseOrder confirmOrder(
            @PathVariable Long id,
            @RequestParam Long client
    ) {
        return orderService.confirmOrder(id, client);
    }

    // PUT /order/{id}/send
    @PutMapping("/{id}/send")
    public PurchaseOrder sendOrder(@PathVariable Long id) {
        return orderService.sendOrder(id);
    }

    // PUT /order/{id}/deliver
    @PutMapping("/{id}/deliver")
    public PurchaseOrder deliverOrder(@PathVariable Long id) {
        return orderService.deliverOrder(id);
    }

    // DELETE /order/{id}
    @DeleteMapping("/{id}")
    public PurchaseOrder deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    // DELETE /order/line/{id}
    @DeleteMapping("/line/{id}")
    public OrderLine deleteOrderLine(@PathVariable Long id) {
        return orderService.deleteOrderLine(id);
    }
}
