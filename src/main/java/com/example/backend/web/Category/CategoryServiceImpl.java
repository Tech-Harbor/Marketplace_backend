package com.example.backend.web.Category;

import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.Category.store.dto.CategoryCreateDTO;
import com.example.backend.web.Category.store.dto.CategoryDTO;
import com.example.backend.web.Category.store.factory.CategoryCreateFactory;
import com.example.backend.web.Category.store.factory.CategoryFactory;
import com.example.backend.web.File.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryCreateFactory categoryCreateFactory;
    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;
    private final ImageService imageService;

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryFactory)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryEntity getCategoryName(final String categoryName) {
        return categoryRepository.getByCategoryName(categoryName);
    }

    @Override
    public CategoryDTO getCategoryDTOName(final String categoryName) {
        return categoryFactory.apply(categoryRepository.getByCategoryName(categoryName));
    }

    @Override
    public CategoryCreateDTO create(final CategoryCreateDTO categoryDTO) {
        final var newImage = imageService.getByImage(categoryDTO.image());

        final var newCategory = CategoryEntity.builder()
                .categoryName(categoryDTO.categoryName())
                .image(newImage)
                .build();

        return categoryCreateFactory.apply(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryCreateDTO update(final Long categoryId, final CategoryCreateDTO categoryDTO) {
        final var updateImage = imageService.getByImage(categoryDTO.image());

        final var category = getIdCategory(categoryId);

        category.setCategoryName(categoryDTO.categoryName());
        category.setImage(updateImage);

        return categoryCreateFactory.apply(categoryRepository.save(category));
    }

    @Override
    public void deleteId(final Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryEntity getIdCategory(final Long id) {
        return categoryRepository.getReferenceById(id);
    }
}