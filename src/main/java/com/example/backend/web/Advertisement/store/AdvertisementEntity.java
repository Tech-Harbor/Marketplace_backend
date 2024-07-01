package com.example.backend.web.Advertisement.store;

import com.example.backend.utils.enums.Delivery;
import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.User.store.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static java.sql.Types.ARRAY;

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

    @Column(columnDefinition = "TEXT")
    private String characteristicAdvertisement; //TODO: Поставити поле на обговорення!

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    @JdbcTypeCode(ARRAY)
    @Enumerated(value = STRING)
    private Set<Delivery> delivery;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(nullable = false)
    private boolean auction;

    @Column(nullable = false)
    private boolean active;
    //TODO: Пояснювальна записка до поля active
    // якщо через 30 днів після створення оголошення ця функція не була включена,
    // то перейде в список не активних оголошень
    // якщо оголошення пробуде 30 днів без активації це оголошення буде видалено назавжди

    @ManyToOne(optional = false)
    private CategoryEntity category;

    @OneToMany
    @JoinColumn(name = "advertisement_id")
    private List<ImageEntity> images;

    @ManyToOne
    private UserEntity user;
}