package com.example.backend.web.Product;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(Long id,ProductEntity entity);
    List<ProductDTO> getAllProduct();
    ProductDTO getOneProduct(Long id);
    ProductDTO editProduct(Long id, ProductEntity entity);
    void deleteIdProduct(Long id);
    void deleteAll();
}