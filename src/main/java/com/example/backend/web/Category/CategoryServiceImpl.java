package com.example.backend.web.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;
    private final EntityManager em;

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

    @Override
    public List<CategoryEntity> getFilterCategory(final String categoryName) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<CategoryEntity> criteriaQuery = criteriaBuilder.createQuery(CategoryEntity.class);

        Root<CategoryEntity> categoryEntityRootRoot = criteriaQuery.from(CategoryEntity.class);

        Predicate categoryNamePredicate = criteriaBuilder.equal(
                categoryEntityRootRoot.get("categoryName"), categoryName);

        criteriaQuery.where(categoryNamePredicate);

        TypedQuery<CategoryEntity> typedQuery = em.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }
}