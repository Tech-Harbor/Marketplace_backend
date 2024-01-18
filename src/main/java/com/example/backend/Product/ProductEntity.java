package com.example.backend.Product;

import com.example.backend.Comment.CommentEntity;
import com.example.backend.ImageFile.ImageFileEntity;
import com.example.backend.Subcategory.SubcategoryEntity;
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

    private LocalDateTime createDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<ImageFileEntity> image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productEntity")
    private List<CommentEntity> commentEntities;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subcategory_id")
    private SubcategoryEntity subcategory;
}