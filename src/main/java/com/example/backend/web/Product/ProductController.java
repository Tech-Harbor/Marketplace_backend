package com.example.backend.web.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;
    private static final String URI_PRODUCTS_ID = "/{id}";
    private static final String URL_DELETE_ALL = "/deleteAll";

    @PostMapping(URI_PRODUCTS_ID)
    @MutationMapping
    public ProductDTO createProductIdByUser(@PathVariable(value = "id") final Long userId,
                                            @RequestBody final ProductDTO entity) {
        return productService.createProduct(userId, entity);
    }

    @GetMapping
    @QueryMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProduct();
    }

    @GetMapping(URI_PRODUCTS_ID)
    @QueryMapping
    public ProductDTO getByIdProduct(@PathVariable final Long id) {
        return productService.getOneProduct(id);
    }

    @PutMapping(URI_PRODUCTS_ID)
    public ProductDTO editProduct(@PathVariable final Long id, @RequestBody final ProductDTO entity) {
        return productService.editProduct(id, entity);
    }

    @DeleteMapping(URI_PRODUCTS_ID)
    public void deleteIdProduct(@PathVariable final Long id) {
        productService.deleteIdProduct(id);
    }

    @DeleteMapping(URL_DELETE_ALL)
    public void deleteAllProduct() {
        productService.deleteAll();
    }
}