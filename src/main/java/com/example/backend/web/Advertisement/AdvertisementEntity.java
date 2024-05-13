package com.example.backend.web.Advertisement;

import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.User.store.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "advertisements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descriptionAdvertisement;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String characteristicAdvertisement;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String location;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private List<ImageEntity> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}