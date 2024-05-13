package com.example.backend.web.Category;

import com.example.backend.web.Category.store.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity getByCategoryName(String categoryName);
}