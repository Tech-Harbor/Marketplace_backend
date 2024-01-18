package com.example.backend.Subcategory;

import com.example.backend.Category.CategoryEntity;
import com.example.backend.Category.CategoryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService{

    private final SubcategoryRepository subcategoryRepository;

    private final SubcategoryFactory subcategoryFactory;

    private final CategoryServiceImpl categoryService;

    @Override
    public List<SubcategoryDTO> getAll() {
        return subcategoryRepository.findAll()
                .stream()
                .map(subcategoryFactory::makeSubcategory)
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryEntity getById(Long id) {
        return subcategoryRepository.getReferenceById(id);
    }

    @Override
    public SubcategoryDTO getOneById(Long id) {
        SubcategoryEntity subcategory = subcategoryRepository.getReferenceById(id);
        return subcategoryFactory.makeSubcategory(subcategory);
    }

    @Override
    public SubcategoryDTO create(SubcategoryDTO subcategoryDTO) {
        CategoryEntity category = categoryService.getById(subcategoryDTO.categoryId());
        SubcategoryEntity newSubcategory = SubcategoryEntity.builder()
                .title(subcategoryDTO.title())
                .additions(subcategoryDTO.additions())
                .category(category)
                .build();

        subcategoryRepository.save(newSubcategory);
        return subcategoryFactory.makeSubcategory(newSubcategory);
    }

    @Override
    public SubcategoryDTO update(Long subcategoryId, SubcategoryDTO subcategoryDTO) {
        CategoryEntity category = categoryService.getById(subcategoryDTO.categoryId());
        SubcategoryEntity subcategory = subcategoryRepository.getReferenceById(subcategoryId);
        subcategory.setTitle(subcategoryDTO.title());

        if (subcategoryDTO.additions() != null) {
            subcategory.setAdditions(subcategoryDTO.additions());
        }

        subcategory.setCategory(category);
        subcategoryRepository.save(subcategory);
        return subcategoryFactory.makeSubcategory(subcategory);
    }

    @Override
    public void delete(Long id) {
        subcategoryRepository.deleteById(id);
    }
}