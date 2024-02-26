package com.example.backend.web.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;

    @Override
    public OrderDTO createOrder(OrderDTO order) {

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
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
