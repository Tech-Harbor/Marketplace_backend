package com.example.backend.Order;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderEntity orderEntity);
    List<OrderDTO> getAllOrder();
    void deleteById(Long id);
}
