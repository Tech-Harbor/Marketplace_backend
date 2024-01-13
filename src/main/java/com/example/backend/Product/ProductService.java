package com.example.backend.Product;

import com.example.backend.Product.ProductDTO;
import com.example.backend.Product.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO entity);
    List<ProductDTO> getAllProduct();
    ProductDTO getOneProduct(Long id);
    ProductDTO editProduct(Long id, ProductEntity entity);
    void deleteIdProduct(Long id);
    void deleteAll();
}