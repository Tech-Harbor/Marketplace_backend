package com.example.backend.web.User;

import com.example.backend.utils.enums.RegisterAuthStatus;
import com.example.backend.utils.enums.Role;
import com.example.backend.web.File.ImageEntity;
import com.example.backend.web.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lastname, firstname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.LAZY)
    private List<ProductEntity> product;

    @OneToOne
    private ImageEntity image;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "register_status")
    private RegisterAuthStatus registerAuthStatus;

    @Builder.Default
    private LocalDateTime createData = LocalDateTime.now();

    private Boolean enabled;
}