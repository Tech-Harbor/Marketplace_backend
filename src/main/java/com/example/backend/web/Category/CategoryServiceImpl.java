package com.example.backend.web.Category;

import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.Category.store.dto.CategoryCreateDTO;
import com.example.backend.web.Category.store.dto.CategoryDTO;
import com.example.backend.web.Category.store.factory.CategoryCreateFactory;
import com.example.backend.web.Category.store.factory.CategoryFactory;
import com.example.backend.web.File.ImageService;
import jakarta.transaction.Transactional;
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
    public CategoryEntity getCategoryName(final String name) {
        return categoryRepository.getByName(name);
    }

    @Override
    public CategoryDTO getCategoryDTOName(final String name) {
        return categoryFactory.apply(categoryRepository.getByName(name));
    }

    @Override
    @Transactional
    public CategoryCreateDTO create(final CategoryCreateDTO categoryDTO) {
        final var newImage = imageService.getByImage(categoryDTO.image());

        final var newCategory = CategoryEntity.builder()
                .name(categoryDTO.name())
                .image(newImage)
                .color(categoryDTO.color())
                .build();

        return categoryCreateFactory.apply(categoryRepository.save(newCategory));
    }

    @Override
    @Transactional
    public CategoryCreateDTO update(final Long categoryId, final CategoryCreateDTO categoryDTO) {
        final var updateImage = imageService.getByImage(categoryDTO.image());

        final var category = categoryRepository.getReferenceById(categoryId);

        category.setName(categoryDTO.name());
        category.setImage(updateImage);
        category.setColor(categoryDTO.color());

        return categoryCreateFactory.apply(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(final CategoryDTO categoryDTO) {
        final var deleteName = getCategoryName(categoryDTO.name());

        categoryRepository.delete(deleteName);
    }
}