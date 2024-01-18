package com.example.backend.ImageFile;


import com.example.backend.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, type;
}