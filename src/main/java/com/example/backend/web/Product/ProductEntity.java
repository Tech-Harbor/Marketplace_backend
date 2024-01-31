package com.example.backend.web.Product;

import com.example.backend.web.Category.CategoryEntity;
import com.example.backend.web.Comment.CommentEntity;
import com.example.backend.web.File.ImageEntity;
import com.example.backend.web.Order.OrderEntity;
import com.example.backend.web.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description_product;

    @Column(columnDefinition = "TEXT")
    private String characteristic_product;

    private double price;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<CommentEntity> commentEntities;
  
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<OrderEntity> orderEntities;
  
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany
    @JoinColumn(name = "photo_id")
    private List<ImageEntity> image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}