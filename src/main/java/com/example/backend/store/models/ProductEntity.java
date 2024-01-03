package com.example.backend.store.models;

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
    //private String customer_reviews;
    private String category;
    private String seller;
    private String number;
    private LocalDateTime createDate = LocalDateTime.now();
    @OneToMany
    private List<ImageFileEntity> image;
}