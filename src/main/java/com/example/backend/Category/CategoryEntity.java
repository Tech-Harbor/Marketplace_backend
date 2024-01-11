package com.example.backend.Category;

import com.example.backend.Section.SectionEntity;
import com.example.backend.Subcategory.SubcategoryEntity;
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

    @ManyToOne
    @JoinColumn(name = "section_id")
    private SectionEntity section;

    @OneToMany
    private List<SubcategoryEntity> subcategoriesList;
}