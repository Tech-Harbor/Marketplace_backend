package com.example.backend.web.Product;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(final Long id, final ProductDTO entity);
    List<ProductDTO> getAllProduct();
    ProductDTO getOneProduct(final Long id);
    ProductDTO editProduct(final Long id, final ProductDTO entity);
    void deleteIdProduct(final Long id);
    void deleteAll();
}