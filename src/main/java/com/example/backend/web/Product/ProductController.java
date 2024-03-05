package com.example.backend.web.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;
    private static final String URI_PRODUCTS_ID = "/{id}";
    private static final String URL_DELETE_ALL = "/deleteAll";

    @PostMapping
    public ProductDTO createProduct(@PathVariable Long id, @RequestBody ProductDTO entity){
        return productService.createProduct(id,entity);
    }

    @GetMapping
    public List<ProductDTO> getProducts(){
        return productService.getAllProduct();
    }

    @GetMapping(URI_PRODUCTS_ID)
    public ProductDTO getOneProduct(@PathVariable Long id){
        return productService.getOneProduct(id);
    }

    @PutMapping(URI_PRODUCTS_ID)
    public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductDTO entity){
        return productService.editProduct(id, entity);
    }

    @DeleteMapping(URI_PRODUCTS_ID)
    public void deleteIdProduct(@PathVariable Long id){
        productService.deleteIdProduct(id);
    }

    @DeleteMapping(URL_DELETE_ALL)
    public void deleteAllProduct(){
        productService.deleteAll();
    }
}