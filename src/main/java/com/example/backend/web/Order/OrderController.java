package com.example.backend.web.Order;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public static final String create_ORDER = "/order";
    public static final String ORDERS = "/orders";
    public static final String ORDER_DELETE_ID = "/delete/order/{id}";

    @PostMapping(create_ORDER)
    public OrderDTO create(@RequestBody OrderEntity order){
        return orderService.createOrder(order);
    }

    @GetMapping(ORDERS)
    public List<OrderDTO> getAllOrder(){
        return orderService.getAllOrder();
    }

    @DeleteMapping(ORDER_DELETE_ID)
    public String deleteById(@PathVariable Long id){
        orderService.deleteById(id);
        return "delete order id: " + id;
    }
}