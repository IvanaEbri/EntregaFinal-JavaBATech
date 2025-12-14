package com.techlab.NookBooks.service;
import com.techlab.NookBooks.exception.*;
import com.techlab.NookBooks.model.dto.OrderLineDTO;
import com.techlab.NookBooks.model.entity.Book;
import com.techlab.NookBooks.model.entity.Client;
import com.techlab.NookBooks.model.entity.OrderLine;
import com.techlab.NookBooks.model.entity.PurchaseOrder;
import com.techlab.NookBooks.repository.OrderLineRepository;
import com.techlab.NookBooks.repository.OrderRepository1;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository1 orderRepository1;
    private OrderLineRepository orderLineRepository;
    private ClientService clientService;
    private BookService bookService;

    public OrderService(OrderRepository1 orderRepository1, OrderLineRepository orderLineRepository, BookService bookService, ClientService clientService){
        this.orderRepository1 = orderRepository1;
        this.orderLineRepository = orderLineRepository;
        this.clientService = clientService;
        this.bookService = bookService;
    }

    public PurchaseOrder createOrder (PurchaseOrder purchaseOrder){
        return this.orderRepository1.save(purchaseOrder);
    }

    public OrderLine createOrderLine (OrderLineDTO dto) throws  NotFoundException, InsufficientStockException {
        PurchaseOrder order = null;

        if (dto.getOrderId() != null) {
            order = orderRepository1.findById(dto.getOrderId())
                    .orElseThrow(() -> new NotFoundException("La orden no existe"));
        } else {
            order = new PurchaseOrder();
            order.setState(0);
            orderRepository1.save(order);
        }

        Book book = bookService.searchBook(dto.getBookId());

        if (book.getStock() < dto.getQuantity()) {
            throw new InsufficientStockException("Stock insuficiente");
        }

        double valueLine = book.getPrice() * dto.getQuantity();

        OrderLine line = new OrderLine();
        line.setOrder(order);
        line.setBook(book);
        line.setQuantity(dto.getQuantity());
        line.setValueLine(valueLine);

        return orderLineRepository.save(line);
    }

    public List<PurchaseOrder> showOrders(Long clientId){
        if (clientId != null){
            return this.orderRepository1.findByClient_Id(clientId);
        }
        return this.orderRepository1.findAll();
        //hasta aca trae todas las ordenes pero no las lineas dentro de cada una, para ello se debe iongresar a la orden para verla
    }

    public List<OrderLine> showOrden (Long orderId) throws NotFoundException, NullException {
        if (orderId == null) {
            throw new NullException("Debe seleccionar un pedido válido.");
        }

        // Validar que la orden exista
        orderRepository1.findById(orderId)
                .orElseThrow(() -> new NotFoundException("El pedido no existe."));

        return orderLineRepository.findByPurchaseOrder_Id(orderId);
        //aca se verian las lineas del pedido
    }

    //no hay nada que editar en la orden, el estado yel cliente se editan en su etapa correspondiente
    /*
    public PurchaseOrder editOrder (Long id, PurchaseOrder dataPurchaseOrder) throws NotFoundException, InvalidEditException {
        PurchaseOrder purchaseOrder = this.orderRepository1.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el pedido"));

        if (purchaseOrder.getState() != 0){
            throw new InvalidEditException("No se puede editar un pedido luego de confirmado.");
        }
        purchaseOrder.setClient(dataPurchaseOrder.getClient());
        return this.orderRepository1.save(purchaseOrder);
    } */

    public OrderLine editOrderLine (Long id, OrderLine dataOrderLine) throws  NotFoundException, InvalidEditException, CheckedDataException, InsufficientStockException {
        OrderLine orderLine = this.orderLineRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontro la linea de pedido"));

        PurchaseOrder purchaseOrder = (PurchaseOrder) orderLine.getOrder();

        if (purchaseOrder.getState() != 0){
            throw new InvalidEditException("No se puede editar un pedido luego de confirmado.");
        }
        if (orderLine.getQuantity() <= 0) {
            throw new CheckedDataException("Debe ingresar una cantidad valida del producto");
        }

        /*
        if (orderLine.getBook()!=dataOrderLine.getBook()) { //si es distinto el libro
            if (dataOrderLine.getQuantity() > dataOrderLine.getBook().getStock()) { //valido q del otro libro tenga stock
                System.out.println("No se hay suficiente stock del libro " + dataOrderLine.getBook().getTitle() + " de " + dataOrderLine.getBook().getAuthor());
                return null;
            }
        }else {
            if (dataOrderLine.getQuantity() < 0) {
                System.out.println("Debe ingresar una cantidad valida para hacer el pedido");
                return null;
            }
            if (dataOrderLine.getQuantity() > orderLine.getQuantity()) { //si es mas cantidad
                Integer quantityRequiered = dataOrderLine.getQuantity() - orderLine.getQuantity();
                if (dataOrderLine.getBook().getStock()< quantityRequiered){
                    System.out.println("No se hay suficiente stock del libro "+ orderLine.getBook().getTitle() + " de "+ orderLine.getBook().getAuthor());
                        return null;
                }
                //si voy a pedir menos ya se que tengo el stock
            }
        }
        try {
            orderLine.getBook().setStock(orderLine.getBook().getStock()+ orderLine.getQuantity());
            dataOrderLine.getBook().setStock(dataOrderLine.getBook().getStock()- dataOrderLine.getQuantity());
            Order editOrder = (Order) orderLine.getOrder();
            Double newPrice = dataOrderLine.getBook().getPrice()* dataOrderLine.getQuantity();
            editOrder.setTotalPrice(editOrder.getTotalPrice()- orderLine.getValueLine()+ newPrice);
            orderLine.setBook(dataOrderLine.getBook());
            orderLine.setQuantity(dataOrderLine.getQuantity());
            orderLine.setValueLine(newPrice);
            editOrder(editOrder.getId(), editOrder);
            return this.createOrderLine(orderLine);
        } catch (Exception e){
            System.out.println("No se pudo modificar la linea de pedido");
            return null;
        }
         */
        purchaseOrder.setTotalPrice(purchaseOrder.getTotalPrice()-orderLine.getValueLine()+ dataOrderLine.getValueLine());

        if (checkStock(dataOrderLine.getBook(),dataOrderLine.getQuantity())){
            throw new InsufficientStockException("No hay stock suficiente para el pedido.");
        }

        orderLine.setBook(dataOrderLine.getBook());
        orderLine.setQuantity(dataOrderLine.getQuantity());


        orderLine.refreshCost();
        this.orderRepository1.save(purchaseOrder);
        return this.orderLineRepository.save(orderLine);
    }

    /*
    public Order deleteOrder (Long id){
        Optional<Order> orderOptional = this.orderRepository1.findById(id);
        if (orderOptional.isEmpty()) {
            System.out.println("No se puede borrar el pedido. No se encontró el mismo");
            return null;
        }

        Order order = orderOptional.get();

        order.setState(4);
        editOrder(order.getId(), order);
        System.out.println("Se ha cancalado el pedido.");
        return order;
    }

    public OrderLine deleteOrderLine (Long id){
        Optional<OrderLine> orderLineOptional = this.orderLineRepository.findById(id);
        if (orderLineOptional.isEmpty()) {
            System.out.println("No se puede borrar la linea de pedido. No se encontró la misma");
            return null;
        }
        OrderLine orderLine = orderLineOptional.get();

        Order editOrder = (Order) orderLine.getOrder();
        editOrder.setTotalPrice(editOrder.getTotalPrice()+ orderLine.getValueLine());
        editOrder(editOrder.getId(),editOrder);

        Book editBook = orderLine.getBook();
        editBook.setStock(editBook.getStock()+ orderLine.getQuantity());


        this.orderLineRepository.delete(orderLine);
        System.out.println("Se ha eliminado la linea de pedido");
        return orderLine;
    }
     */

    private Boolean checkStock(Book book, Integer quantity) throws NotFoundException {
        Optional<Book> bookOptional = Optional.ofNullable(this.bookService.searchBook(book.getId()));
        if (bookOptional.isEmpty()) {
            throw new NotFoundException("No se ha encontrado el libro.");
        }

        Book bookSelected = bookOptional.get();
        if (bookSelected.getStock() < quantity){
            return false;
        }
        return true;
    }

    @Transactional
    public PurchaseOrder confirmOrder (Long id, Long clientId) throws NotFoundException, InsufficientStockException {
        PurchaseOrder purchaseOrder = this.orderRepository1.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el el pedido."));
        Client client = this.clientService.searchClient(clientId);

        List<OrderLine> orderLines= orderLineRepository.findByPurchaseOrder_Id(purchaseOrder.getId());

        for (OrderLine orderLine : orderLines){
            Boolean stock = this.checkStock(orderLine.getBook(),orderLine.getQuantity());
            if (!stock) {
                throw new InsufficientStockException(orderLine.getBook().getTitle());
            }
        }
        Double i = 0.00;
        for (OrderLine orderLine1 : orderLines) {
            orderLine1.refreshCost();
            orderLineRepository.save(orderLine1);
            i += orderLine1.getValueLine();
            bookService.removeStock(orderLine1.getId(), orderLine1.getQuantity());
        }
        purchaseOrder.setTotalPrice(i);
        purchaseOrder.setClient(client);
        purchaseOrder.setState(1);
        orderRepository1.save(purchaseOrder);
        return purchaseOrder;
    }

    public PurchaseOrder sendOrder (Long id) throws NotFoundException{
        PurchaseOrder purchaseOrder = this.orderRepository1.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el libro."));
        purchaseOrder.setState(2);
        orderRepository1.save(purchaseOrder);
        return purchaseOrder;
    }

    public PurchaseOrder deliverOrder (Long id) throws  NotFoundException {
        PurchaseOrder purchaseOrder = this.orderRepository1.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el libro."));
        purchaseOrder.setState(3);
        orderRepository1.save(purchaseOrder);
        return purchaseOrder;
    }

    public PurchaseOrder deleteOrder (Long id) throws  NotFoundException{
        PurchaseOrder purchaseOrder = this.orderRepository1.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el libro."));
        if (purchaseOrder.getState()!= 0 && purchaseOrder.getState() != 4){
            List<OrderLine> orderLines= orderLineRepository.findByPurchaseOrder_Id(purchaseOrder.getId());

            for (OrderLine orderLine1 : orderLines) {
                bookService.addStock(orderLine1.getId(), orderLine1.getQuantity());
            }
        }
        purchaseOrder.setState(4);
        orderRepository1.save(purchaseOrder);
        return purchaseOrder;
    }

    public OrderLine deleteOrderLine (Long id) throws NotFoundException {
        OrderLine orderLine = this.orderLineRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el la línea de pedido."));

        PurchaseOrder purchaseOrder = (PurchaseOrder) orderLine.getOrder();

        if (purchaseOrder.getState() != 0){
            throw new InvalidEditException("No se pueden eliminar líneas de los pedidos confirmados.");
        }

        purchaseOrder.setTotalPrice(purchaseOrder.getTotalPrice()-orderLine.getValueLine());
        orderRepository1.save(purchaseOrder);

        orderLineRepository.delete(orderLine);
        return orderLine;
    }

}
