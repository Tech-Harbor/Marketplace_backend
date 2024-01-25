package com.example.backend.Category;

import com.example.backend.Product.ProductEntity;
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
    private String title, information;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category")
    private List<ProductEntity> productEntityList;
}