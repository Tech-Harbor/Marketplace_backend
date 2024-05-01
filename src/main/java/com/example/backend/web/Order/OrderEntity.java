package com.example.backend.web.Order;

import com.example.backend.web.Product.ProductEntity;
import com.example.backend.web.User.UserEntity;
import com.example.backend.utils.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}