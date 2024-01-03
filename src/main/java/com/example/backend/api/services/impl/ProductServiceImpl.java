package com.example.backend.api.services.impl;

import com.example.backend.api.DTO.ProductDTO;
import com.example.backend.api.factory.ProductFactory;
import com.example.backend.api.services.ImageFileService;
import com.example.backend.api.services.ProductService;
import com.example.backend.store.models.ProductEntity;
import com.example.backend.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductFactory productFactory;
    private final ImageFileService imageService;
    @Override
    public ProductDTO createProduct(ProductEntity entity) {
        ProductEntity product = productRepository.save(entity);
        ProductDTO productDTO = productFactory.makeProduct(product);
        imageService.setProductEntity(product);
        return productDTO;
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.findAll().stream().map(productFactory::makeProduct).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getOneProduct(Long id) {
        ProductEntity entity_Id = productRepository.getReferenceById(id);

        return productFactory.makeProduct(entity_Id);
    }

    @Override
    public ProductDTO editProduct(Long id, ProductEntity entity) {
        ProductEntity entityId = productRepository.getReferenceById(id);

       ProductEntity saveProduct = ProductEntity.builder()
               .id(entityId.getId())
               .description_product(entity.getDescription_product())
               .name(entityId.getName())
               .category(entity.getCategory())
               .number(entity.getNumber())
               .createDate(entity.getCreateDate())
               .seller(entityId.getSeller())
               .characteristic_product(entity.getCharacteristic_product())
               .image(entityId.getImage())
               .price(entityId.getPrice())
               .build();

        return productFactory.makeProduct(productRepository.save(saveProduct));
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
