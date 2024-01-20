package com.example.backend.Product;

import com.example.backend.Comment.CommentEntity;
import com.example.backend.ImageFile.ImageFileEntity;
import com.example.backend.Order.OrderEntity;
import com.example.backend.Subcategory.SubcategoryEntity;
import com.example.backend.User.UserEntity;
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

    @OneToMany
    @JoinColumn(name = "image_id")
    private List<ImageFileEntity> image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<CommentEntity> commentEntities;
  
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<OrderEntity> orderEntities;
  
    @ManyToOne(optional = false)
    @JoinColumn(name = "subcategory_id")
    private SubcategoryEntity subcategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}