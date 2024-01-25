package com.example.backend.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductServiceImpl productService;
    private static final String URI_PRODUCTS = "/products";
    private static final String URI_CREATE_UPDATE_GET_DELETE = "/product/{id}";
    private static final String URL_DELETE_ALL = "/deleteAll";

    @PostMapping(URI_CREATE_UPDATE_GET_DELETE)
    public ProductDTO createProduct(@PathVariable Long id, @RequestBody ProductEntity entity){
        return productService.createProduct(id,entity);
    }

    @GetMapping(URI_PRODUCTS)
    public List<ProductDTO> getProducts(){
        return productService.getAllProduct();
    }

    @GetMapping(URI_CREATE_UPDATE_GET_DELETE)
    public ProductDTO getOneProduct(@PathVariable Long id){
        return productService.getOneProduct(id);
    }

    @PutMapping(URI_CREATE_UPDATE_GET_DELETE)
    public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductEntity entity){
        return productService.editProduct(id, entity);
    }

    @DeleteMapping(URI_CREATE_UPDATE_GET_DELETE)
    public void deleteIdProduct(@PathVariable Long id){
        productService.deleteIdProduct(id);
    }

    @DeleteMapping(URL_DELETE_ALL)
    public void deleteAllProduct(){
        productService.deleteAll();
    }
}