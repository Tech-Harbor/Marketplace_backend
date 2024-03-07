package com.example.backend.web.Product;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(Long id, ProductDTO entity);
    List<ProductDTO> getAllProduct();
    ProductDTO getOneProduct(Long id);
    ProductDTO editProduct(Long id, ProductDTO entity);
    void deleteIdProduct(Long id);
    void deleteAll();
}