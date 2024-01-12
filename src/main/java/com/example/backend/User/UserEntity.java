package com.example.backend.User;

import com.example.backend.Comment.CommentEntity;
import com.example.backend.Order.OrderEntity;
import com.example.backend.utils.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastname, name, email, number;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity commentEntity;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
