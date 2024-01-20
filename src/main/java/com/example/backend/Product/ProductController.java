package com.example.backend.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductServiceImpl productService;
    private static final String URI_PRODUCTS = "/products";
    private static final String URI_PRODUCT = "/product/{id}";
    private static final String URI_PRODUCT_ID = "/products/{id}";

    @PostMapping(URI_PRODUCT)
    public ProductDTO createProduct(@PathVariable Long id, @RequestBody ProductEntity entity){
        return productService.createProduct(id,entity);
    }

    @GetMapping(URI_PRODUCTS)
    public List<ProductDTO> getProducts(){
        return productService.getAllProduct();
    }

    @GetMapping(URI_PRODUCT_ID)
    public ProductDTO getOneProduct(@PathVariable Long id){
        return productService.getOneProduct(id);
    }

    @PutMapping(URI_PRODUCT_ID)
    public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductEntity entity){
        return productService.editProduct(id, entity);
    }

    @DeleteMapping(URI_PRODUCT_ID)
    public void deleteIdProduct(@PathVariable Long id){
        productService.deleteIdProduct(id);
    }

    @DeleteMapping(URI_PRODUCTS)
    public void deleteAllProduct(){
        productService.deleteAll();
    }
}