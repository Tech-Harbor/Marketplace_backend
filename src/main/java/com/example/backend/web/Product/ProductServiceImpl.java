package com.example.backend.web.Product;

import com.example.backend.web.Category.CategoryEntity;
import com.example.backend.web.Category.CategoryService;
import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductFactory productFactory;
    private final UserService userService;
    private final EntityManager em;

    @Override
    public ProductDTO createProduct(final Long id, final ProductDTO product) {
        final UserEntity userId = userService.getById(id);
        final CategoryEntity categoryId = categoryService.getById(product.categoryId());

        final ProductEntity newProduct = ProductEntity.builder()
                .id(product.id())
                .user(userId)
                .name(product.name())
                .characteristicProduct(product.characteristicProduct())
                .descriptionProduct(product.descriptionProduct())
                .price(product.price())
                .images(product.images())
                .location(product.location())
                .createDate(LocalDateTime.now())
                .category(categoryId)
                .build();

        return productFactory.makeProduct(productRepository.save(newProduct));
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.findAll().stream()
                .map(productFactory::makeProduct)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getOneProduct(final Long id) {
        final ProductEntity entityId = getIdProduct(id);

        return productFactory.makeProduct(entityId);
    }

    @Override
    public ProductDTO editProduct(final Long id, final ProductDTO entity) {
        final ProductEntity entityId = getIdProduct(id);

            entityId.setName(entity.name());
            entityId.setCharacteristicProduct(entity.characteristicProduct());
            entityId.setDescriptionProduct(entity.descriptionProduct());
            entityId.setCreateDate(entity.createDate());
            entityId.setPrice(entity.price());

        return productFactory.makeProduct(productRepository.save(entityId));
    }

    @Override
    public void deleteIdProduct(final Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Override
    public List<ProductEntity> getFilterProductName(final String name) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);

        Root<ProductEntity> productEntityRoot = criteriaQuery.from(ProductEntity.class);

        Predicate nameProductPredicate = criteriaBuilder.equal(productEntityRoot.get("name"), name);

        criteriaQuery.where(nameProductPredicate);

        TypedQuery<ProductEntity> typedQuery = em.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private ProductEntity getIdProduct(final Long id) {
        return productRepository.getReferenceById(id);
    }
}
