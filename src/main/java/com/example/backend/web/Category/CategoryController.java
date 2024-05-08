package com.example.backend.web.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private static final String URI_CATEGORIES_ID = "/{id}";
    private static final String URI_CATEGORIES_FILTER = "/filter/{categoryName}";

    @GetMapping
    @QueryMapping
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getAll();
    }

    @QueryMapping
    public CategoryDTO getByIdCategory(@Argument final Long id) {
        return categoryService.getOneById(id);
    }

    @PostMapping
    public CategoryDTO create(@RequestBody @Validated final CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @PutMapping(URI_CATEGORIES_ID)
    public CategoryDTO update(@PathVariable final Long id, @RequestBody @Validated final CategoryDTO categoryDTO) {
        return categoryService.update(id, categoryDTO);
    }

    @GetMapping(URI_CATEGORIES_FILTER)
    public List<CategoryEntity> getFilterLatsName(@PathVariable final String categoryName) {
        return categoryService.getFilterCategory(categoryName);
    }

    @DeleteMapping(URI_CATEGORIES_ID)
    public void delete(@PathVariable final Long id) {
        categoryService.deleteId(id);
    }
}