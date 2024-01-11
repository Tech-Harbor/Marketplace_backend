package com.example.backend.Order;

import com.example.backend.User.UserEntity;
import com.example.backend.utils.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany
    private List<UserEntity> userEntities;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}