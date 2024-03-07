package com.example.backend.web.Product;

import com.example.backend.web.Category.CategoryEntity;
import com.example.backend.web.Category.CategoryService;
import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private ProductFactory productFactory;
    private CategoryService categoryService;

    @Override
    public ProductDTO createProduct(final Long id, final ProductDTO product) {
        final UserEntity userId = userService.getById(id);
        final CategoryEntity categoryId = categoryService.getById(product.categoryId());

        final ProductEntity newProduct = ProductEntity.builder()
                .id(product.id())
                .user(userId)
                .name(product.name())
                .description_product(product.description_product())
                .characteristic_product(product.characteristic_product())
                .price(product.price())
                .createDate(product.createDate())
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
        final ProductEntity entity_Id = productRepository.getReferenceById(id);

        return productFactory.makeProduct(entity_Id);
    }

    @Override
    public ProductDTO editProduct(final Long id, final ProductDTO entity) {
        final ProductEntity entityId = productRepository.getReferenceById(id);
               entityId.setDescription_product(entity.description_product());
               entityId.setName(entity.name());
               entityId.setCreateDate(entity.createDate());
               entityId.setCharacteristic_product(entity.characteristic_product());
               entityId.setPrice(entity.price());
               entityId.setDescription_product(entity.description_product());

        return productFactory.makeProduct(productRepository.save(entityId));
    }

    @Override
    public void deleteIdProduct(final Long id) {
        productRepository.deleteById(id);
    }
    @Override
    public void deleteAll(){
        productRepository.deleteAll();
    }
}
