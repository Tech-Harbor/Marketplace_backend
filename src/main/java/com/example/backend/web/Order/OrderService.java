package com.example.backend.web.Order;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(final OrderDTO order);
    List<OrderDTO> getAllOrder();
    void deleteById(final Long id);
}
