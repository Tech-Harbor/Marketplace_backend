package com.example.backend.web.Order;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO order);
    List<OrderDTO> getAllOrder();
    void deleteById(Long id);
}
