package com.example.backend.web.Product;

import com.example.backend.web.User.UserEntity;
import com.example.backend.web.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
  
    private final UserRepository userRepository;

    private final ProductFactory productFactory;

     @Override
    public ProductDTO createProduct(Long id, ProductEntity product) {
        UserEntity userId = userRepository.getReferenceById(id);
        product.setUser(userId);
        return productFactory.makeProduct(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.findAll().stream()
                .map(productFactory::makeProduct)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getOneProduct(Long id) {
        ProductEntity entity_Id = productRepository.getReferenceById(id);

        return productFactory.makeProduct(entity_Id);
    }

    @Override
    public ProductDTO editProduct(Long id, ProductEntity entity) {
        ProductEntity entityId = productRepository.getReferenceById(id);
               entityId.setDescription_product(entity.getDescription_product());
               entityId.setName(entityId.getName());
               entityId.setCreateDate(entity.getCreateDate());
               entityId.setCharacteristic_product(entity.getCharacteristic_product());
               entityId.setPrice(entityId.getPrice());

        return productFactory.makeProduct(productRepository.save(entityId));
    }

    @Override
    public void deleteIdProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Override
    public void deleteAll(){
        productRepository.deleteAll();
    }
}
