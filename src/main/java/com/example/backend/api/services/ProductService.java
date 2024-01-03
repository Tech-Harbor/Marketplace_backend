package com.example.backend.api.services;

import com.example.backend.api.DTO.ProductDTO;
import com.example.backend.store.models.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductEntity entity);
    List<ProductDTO> getAllProduct();
    ProductDTO getOneProduct(Long id);
    ProductDTO editProduct(Long id, ProductEntity entity);
    void deleteIdProduct(Long id);
    void deleteAll();
}