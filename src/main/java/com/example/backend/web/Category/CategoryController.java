package com.example.backend.web.Category;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "CategoryService")
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private static final String URI_CATEGORIES_ID = "/{id}";

    @GetMapping
    @QueryMapping
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getAll();
    }

    @QueryMapping
    public CategoryDTO getByNameCategory(@Argument final String name) {
        return categoryService.getCategoryDTOName(name);
    }

    @PostMapping
    public CategoryDTO create(@RequestBody @Validated final CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @PutMapping(URI_CATEGORIES_ID)
    public CategoryDTO update(@PathVariable final Long id, @RequestBody @Validated final CategoryDTO categoryDTO) {
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping(URI_CATEGORIES_ID)
    public void delete(@PathVariable final Long id) {
        categoryService.deleteId(id);
    }
}