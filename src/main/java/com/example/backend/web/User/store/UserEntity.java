package com.example.backend.web.User.store;

import com.example.backend.utils.enums.RegisterAuthStatus;
import com.example.backend.utils.enums.Role;
import com.example.backend.utils.enums.Status;
import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import com.example.backend.web.File.store.ImageEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static java.sql.Types.ARRAY;

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
    private List<AdvertisementEntity> advertisements;

    @OneToOne
    private ImageEntity image;

    @Enumerated(value = STRING)
    @JdbcTypeCode(ARRAY)
    private Set<Role> roles;

    @Enumerated(value = STRING)
    @Column(name = "register_status")
    private RegisterAuthStatus registerAuthStatus;

    @Builder.Default
    private LocalDateTime createData = LocalDateTime.now();

    private Boolean enabled;

    @Column(name = "account_expired")
    private Boolean accountNonExpired;

    @Column(name = "account_locked")
    private Boolean accountNonLocked;

    @Column(name = "credentials_expired")
    private Boolean credentialsNonExpired;

    @Enumerated(value = STRING)
    private Status status;
}