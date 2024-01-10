package com.example.backend.ImageFile;


import com.example.backend.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @ManyToOne
    private ProductEntity product;
}