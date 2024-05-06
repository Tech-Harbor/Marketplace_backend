package com.example.backend.web.Product;

import com.example.backend.web.Category.CategoryEntity;
import com.example.backend.web.File.ImageEntity;
import com.example.backend.web.User.store.UserEntity;
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

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descriptionProduct;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String characteristicProduct;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String location;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany
    @JoinColumn(name = "photo_id")
    private List<ImageEntity> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}