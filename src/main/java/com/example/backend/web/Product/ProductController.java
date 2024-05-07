package com.example.backend.web.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
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
    private static final String URI_PRODUCTS_FILTER = "/filter/{name}";
    private static final String URL_DELETE_ALL = "/deleteAll";

    @PostMapping(URI_PRODUCTS_ID)
    @MutationMapping
    public ProductDTO createProductIdByUser(@PathVariable(value = "id") @Argument final Long userId,
                                            @RequestBody @Argument final ProductDTO entity) {
        return productService.createProduct(userId, entity);
    }

    @QueryMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProduct();
    }

    @QueryMapping
    public ProductDTO getByIdProduct(@PathVariable @Argument final Long id) {
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

    @GetMapping(URI_PRODUCTS_FILTER)
    public List<ProductEntity> getFilterName(@PathVariable final String name) {
        return productService.getFilterProductName(name);
    }

    @DeleteMapping(URL_DELETE_ALL)
    public void deleteAllProduct() {
        productService.deleteAll();
    }
}