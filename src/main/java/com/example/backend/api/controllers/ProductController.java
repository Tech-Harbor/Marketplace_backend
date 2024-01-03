package com.example.backend.api.controllers;

import com.example.backend.api.DTO.ProductDTO;
import com.example.backend.api.services.impl.ProductServiceImpl;
import com.example.backend.store.models.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private static final String CREATE_PRODUCT = "/product";
    private static final String GET_PRODUCTS = "/products";
    private static final String PRODUCT_ID = "/product/{id}"; //GET AND PUT
    private static final String DELETE_PRODUCT = "/delete/product/{id}";
    private static final String DELETE_PRODUCTS_All = "/delete/products";

    @PostMapping(CREATE_PRODUCT)
    public ProductDTO createProduct(@RequestBody ProductEntity entity){
        return productService.createProduct(entity);
    }

    @GetMapping(GET_PRODUCTS)
    public List<ProductDTO> getProducts(){
        return productService.getAllProduct();
    }

    @GetMapping(PRODUCT_ID)
    public ProductDTO getOneProduct(@PathVariable Long id){
        return productService.getOneProduct(id);
    }

    @PutMapping(PRODUCT_ID)
    public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductEntity entity){
        return productService.editProduct(id, entity);
    }

    @DeleteMapping(DELETE_PRODUCT)
    public void deleteIdProduct(@PathVariable Long id){
        productService.deleteIdProduct(id);
    }

    @DeleteMapping(DELETE_PRODUCTS_All)
    public void deleteAllProduct(){
        productService.deleteAll();
    }
}