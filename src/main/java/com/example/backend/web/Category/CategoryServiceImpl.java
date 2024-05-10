package com.example.backend.web.Category;

import com.example.backend.web.File.ImageService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;
    private final ImageService imageService;
    private final EntityManager em;

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
    public CategoryDTO getOneById(final Long id) {
        final var category = getIdCategory(id);

        return categoryFactory.apply(category);
    }

    @Override
    public CategoryDTO create(final CategoryDTO categoryDTO) {
        final var newImage = imageService.getByImage(categoryDTO.image());

        final var newCategory = CategoryEntity.builder()
                .categoryName(categoryDTO.categoryName())
                .image(newImage)
                .build();

        return categoryFactory.apply(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryDTO update(final Long categoryId, final CategoryDTO categoryDTO) {
        final var updateImage = imageService.getByImage(categoryDTO.image());

        final var category = getIdCategory(categoryId);

        category.setCategoryName(categoryDTO.categoryName());
        category.setImage(updateImage);

        return categoryFactory.apply(categoryRepository.save(category));
    }

    @Override
    public void deleteId(final Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryEntity getIdCategory(final Long id) {
        return categoryRepository.getReferenceById(id);
    }

    @Override
    public List<CategoryEntity> getFilterCategory(final String categoryName) {
        final var criteriaBuilder = em.getCriteriaBuilder();

        final var criteriaQuery = criteriaBuilder.createQuery(CategoryEntity.class);

        final var categoryEntityRootRoot = criteriaQuery.from(CategoryEntity.class);

        final var categoryNamePredicate = criteriaBuilder.equal(
                categoryEntityRootRoot.get("categoryName"), categoryName
        );

        criteriaQuery.where(categoryNamePredicate);

        final var typedQuery = em.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }
}