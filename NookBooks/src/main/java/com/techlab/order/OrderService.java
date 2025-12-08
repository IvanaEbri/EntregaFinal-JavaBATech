package com.techlab.order;
import com.techlab.book.Book;
import com.techlab.book.BookService;
import com.techlab.client.Client;
import com.techlab.client.ClientService;
import com.techlab.exception.ExceptionStockInsuficiente;
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

    public Order createOrder (Order order){
        return this.orderRepository1.save(order);
    }

    public OrderLine createOrderLine (OrderLine orderLine){
        Order order;
        try {
            order = (Order) orderLine.getOrder();
            //si no hay una orden creada se deberá crear
        } catch (Exception e){
            order = new Order();
            createOrder(order);
        }

        if(order.getState() !=0){
            System.out.println("No se pueden agregar mas items al pedido n "+ order.getId() + " de "+ order.getClient().getClientName());
            return null;
        }
        if (!this.checkStock(orderLine.getBook(),orderLine.getQuantity())){
            System.out.println("No se hay suficiente stock del libro "+ orderLine.getBook().getTitle() + " de "+ orderLine.getBook().getAuthor());
            return null;
        }
        if (!orderLine.getBook().getActive()){
            System.out.println("El libro que desea ingeresar ha sido eliminado");
            return null;
        }
        //al crear la linea no se deberia restar del stock inmediatamente
/*        try {
            orderLine.getBook().setStock(orderLine.getBook().getStock()- orderLine.getQuantity());
            order.setTotalPrice(order.getTotalPrice()+orderLine.getValueLine());
            return this.orderLineRepository.save(orderLine);

        } catch (Exception e){
            System.out.println("Ha ocurrido un error al crear el pedido del libro");
            return null;
        }
 */
        return this.orderLineRepository.save(orderLine);
    }

    public List<Order> showOrders(Long clientId){
        if (clientId != null){
            return this.orderRepository1.findByClient_ClientId(clientId);
        }
        return this.orderRepository1.findAll();
        //hasta aca trae todas las ordenes pero no las lineas dentro de cada una, para ello se debe iongresar a la orden para verla
    }

    public List<OrderLine> showOrden (Long orderId){
        if (orderId == null){
            System.out.println("No ha seleccionado un pedido para mostrar");
            return null;
        }
        try {
            return this.orderLineRepository.findByOrder_OrderId(orderId);
        }catch (Exception e){
            System.out.println("El pedido seleccionado no es valido");
            return null;
        }
        //aca se verian las lineas del pedido
    }

    public Order editOrder (Long id, Order dataOrder){
        Order order = this.orderRepository1.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el pedido"));
        if (dataOrder.getClient() == null){
            System.out.println("No se puede editar el pedido. Debe ingresar un cliente valido.");
            return null;
        }

        if (order.getState() != 0){
            System.out.println("No se puede editar un pedido luego de confirmado.");
        }
        order.setClient(dataOrder.getClient());
        return this.orderRepository1.save(order);
    }

    public OrderLine editOrderLine (Long id, OrderLine dataOrderLine){
        OrderLine orderLine = this.orderLineRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontro la linea de pedido"));

        Order order = (Order) orderLine.getOrder();

        if (order.getState() != 0){
            System.out.println("No se puede editar un pedido confirmado");
            return null;
        }
        if (orderLine.getQuantity() <= 0) {
            System.out.println("Debe ingresar una cantidad valida del producto");
            return null;
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
        order.setTotalPrice(order.getTotalPrice()-orderLine.getValueLine()+ dataOrderLine.getValueLine());
        orderLine.setBook(dataOrderLine.getBook());
        orderLine.setQuantity(dataOrderLine.getQuantity());
        orderLine.setValueLine(dataOrderLine.getValueLine());
        this.orderRepository1.save(order);
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

    private Boolean checkStock(Book book, Integer quantity){
        Optional<Book> bookOptional = this.bookService.showBook(book.getId());
        if (bookOptional.isEmpty()) {
            System.out.println("No se ha econtrado el libro.");
            return false;
        }

        Book bookSelected = bookOptional.get();
        if (bookSelected.getStock() < quantity){
            System.out.println("No hay stock para el pedido");
            return false;
        }
        return true;
    }

    @Transactional
    public Order confirmOrder (Long id, Long clientId){
        Order order = this.orderRepository1.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el libro."));
        Client client = this.clientService.searchClient(clientId);

        List<OrderLine> orderLines= orderLineRepository.findByOrder_OrderId(order.getId());

        for (OrderLine orderLine : orderLines){
            Boolean stock = this.checkStock(orderLine.getBook(),orderLine.getQuantity());
            if (!stock) {
                try {
                    throw new ExceptionStockInsuficiente(orderLine.getBook().getTitle());
                } catch (ExceptionStockInsuficiente e) {
                    throw new RuntimeException(e);
                }
            }
        }
        Double i = 0.00;
        for (OrderLine orderLine1 : orderLines) {
            orderLine1.setValueLine(orderLine1.getBook().getPrice()*orderLine1.getQuantity());
            orderLineRepository.save(orderLine1);
            i += orderLine1.getValueLine();
            bookService.removeStock(orderLine1.getId(), orderLine1.getQuantity());
        }
        order.setTotalPrice(i);
        order.setClient(client);
        order.setState(1);
        orderRepository1.save(order);
        System.out.println("Se ha confirmado el pedido");
        return order;
    }

    public Order sendOrder (Long id) {
        Order order = this.orderRepository1.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el libro."));
        order.setState(2);
        orderRepository1.save(order);
        System.out.println("Se ha enviado el pedido");
        return order;
    }

    public Order deliverOrder (Long id) {
        Order order = this.orderRepository1.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el libro."));
        order.setState(3);
        orderRepository1.save(order);
        System.out.println("Se ha entregado el pedido");
        return order;
    }

    public Order deleteOrder (Long id) {
        Order order = this.orderRepository1.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el libro."));
        if (order.getState()!= 0 && order.getState() != 4){
            List<OrderLine> orderLines= orderLineRepository.findByOrder_OrderId(order.getId());

            for (OrderLine orderLine1 : orderLines) {
                bookService.addStock(orderLine1.getId(), orderLine1.getQuantity());
            }
        }
        order.setState(4);
        orderRepository1.save(order);
        System.out.println("Se ha cancelado el pedido");
        return order;
    }

    public OrderLine deleteOrderLine (Long id){
        OrderLine orderLine = this.orderLineRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el la línea de pedido."));

        Order order = (Order) orderLine.getOrder();

        if (order.getState() != 0){
            System.out.println("No se pueden eliminar líneas de los pedidos confirmados.");
            return null;
        }

        order.setTotalPrice(order.getTotalPrice()-orderLine.getValueLine());
        orderRepository1.save(order);

        orderLineRepository.delete(orderLine);
        System.out.println("Se ha eliminado la línea de pedido");
        return orderLine;
    }

}
