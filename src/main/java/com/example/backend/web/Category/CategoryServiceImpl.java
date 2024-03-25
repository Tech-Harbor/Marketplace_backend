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
    public CategoryEntity getById(final Long id) {
        return getIdCategory(id);
    }

    @Override
    public CategoryDTO getOneById(final Long id) {
        CategoryEntity category = getIdCategory(id);

        return categoryFactory.makeCategory(category);
    }

    @Override
    public CategoryDTO create(final CategoryDTO categoryDTO) {
        CategoryEntity newCategory = CategoryEntity.builder()
                .categoryName(categoryDTO.categoryName())
                .build();

        return categoryFactory.makeCategory(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryDTO update(final Long categoryId, final CategoryDTO categoryDTO) {
        CategoryEntity category = getIdCategory(categoryId);

        category.setCategoryName(category.getCategoryName());

        return categoryFactory.makeCategory(categoryRepository.save(category));
    }

    @Override
    public void deleteId(final Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryEntity getIdCategory(final Long id) {
        return categoryRepository.getReferenceById(id);
    }
}