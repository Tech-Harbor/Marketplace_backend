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
    private List<UserEntity> userEntities;

    private String text;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
}