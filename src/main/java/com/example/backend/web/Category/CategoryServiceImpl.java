package com.example.backend.web.Category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryFactory categoryFactory;

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryFactory::makeCategory)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryEntity getById(Long id) {
        return categoryRepository.getReferenceById(id);
    }

    @Override
    public CategoryDTO getOneById(Long id) {
        CategoryEntity category = categoryRepository.getReferenceById(id);
        return categoryFactory.makeCategory(category);
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        CategoryEntity newCategory = CategoryEntity.builder()
                .category_name(categoryDTO.category_name())
                .build();

        categoryRepository.save(newCategory);
        return categoryFactory.makeCategory(newCategory);
    }

    @Override
    public CategoryDTO update(Long categoryId, CategoryDTO categoryDTO) {
        CategoryEntity category = categoryRepository.getReferenceById(categoryId);
        categoryDTO.category_name();
        categoryRepository.save(category);
        return categoryFactory.makeCategory(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
