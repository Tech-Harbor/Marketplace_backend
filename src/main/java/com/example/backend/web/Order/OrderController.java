package com.example.backend.web.Order;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    public static final String URI_ORDERS_ID = "/{id}";

    @PostMapping
    public OrderDTO create(@RequestBody final OrderDTO order){
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<OrderDTO> getAllOrder(){
        return orderService.getAllOrder();
    }

    @DeleteMapping(URI_ORDERS_ID)
    public String deleteById(@PathVariable final Long id){
        orderService.deleteById(id);
        return "delete order id: " + id;
    }
}