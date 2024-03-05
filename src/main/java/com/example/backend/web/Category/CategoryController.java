package com.example.backend.web.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private static final String URI_CATEGORIES_ID = "/{id}";

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @GetMapping(URI_CATEGORIES_ID)
    public CategoryDTO getOneById(@PathVariable Long id) {
        return categoryService.getOneById(id);
    }

    @PostMapping
    public CategoryDTO create(@RequestBody @Validated CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @PutMapping(URI_CATEGORIES_ID)
    public CategoryDTO update(@PathVariable Long id, @RequestBody @Validated CategoryDTO categoryDTO) {
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping(URI_CATEGORIES_ID)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}