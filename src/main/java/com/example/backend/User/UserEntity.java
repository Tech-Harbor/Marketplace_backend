package com.example.backend.User;

import com.example.backend.Comment.CommentEntity;
import com.example.backend.Order.OrderEntity;
import com.example.backend.utils.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<OrderEntity> orderEntity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<CommentEntity> comments;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
