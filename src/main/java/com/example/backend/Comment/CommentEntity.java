package com.example.backend.Comment;


import com.example.backend.Product.ProductEntity;
import com.example.backend.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserEntity> userEntities;
    private String text;
    @ManyToOne
    private ProductEntity productEntity;
}
