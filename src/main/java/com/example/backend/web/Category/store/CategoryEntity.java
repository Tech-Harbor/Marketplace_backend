package com.example.backend.web.Category.store;

import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.Advertisement.store.AdvertisementEntity;
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
    private String name;

    @Column(nullable = false)
    private String color;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category", fetch = FetchType.LAZY)
    private List<AdvertisementEntity> advertisements;

    @OneToOne
    private ImageEntity image;
}