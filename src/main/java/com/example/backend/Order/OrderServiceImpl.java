package com.example.backend.Order;

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
    public OrderDTO createOrder(OrderEntity orderEntity) {
        return orderFactory.makeOrderDTO(orderRepository.save(orderEntity));
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
