package com.example.backend.api.config.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQConsumer {

    @RabbitListener(queues = "queues_market")
    public void processMyQueue(String message) {
        System.out.printf("Received from myQueue : %s ", new String(message.getBytes()));
    }
}