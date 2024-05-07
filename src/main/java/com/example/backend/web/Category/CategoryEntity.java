package com.example.backend.web.Category;

import com.example.backend.web.File.ImageEntity;
import com.example.backend.web.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category", fetch = FetchType.LAZY)
    private List<ProductEntity> products;

    @OneToOne
    private ImageEntity image;
}