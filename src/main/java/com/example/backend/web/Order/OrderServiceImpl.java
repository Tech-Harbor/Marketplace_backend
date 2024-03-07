package com.example.backend.web.Order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private OrderFactory orderFactory;

    @Override
    public OrderDTO createOrder(final OrderDTO order) {

        OrderEntity newOrder = OrderEntity.builder()
                .id(order.id())
                .product(order.product())
                .user(order.user())
                .status(order.status())
                .build();

        return orderFactory.makeOrderDTO(orderRepository.save(newOrder));
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        return orderRepository.findAll().stream().map(orderFactory::makeOrderDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Long id) {
        orderRepository.deleteById(id);
    }
}
