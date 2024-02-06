package com.example.backend.web.Category;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private static final String URI_CATEGORIES = "/categories";
    private static final String URI_CATEGORY_ID = "/categories/{id}";

    @GetMapping(URI_CATEGORIES)
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @GetMapping(URI_CATEGORY_ID)
    public CategoryDTO getOneById(@PathVariable Long id) {
        return categoryService.getOneById(id);
    }

    @PostMapping(URI_CATEGORIES)
    public CategoryDTO create(@RequestBody @Validated CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @PutMapping(URI_CATEGORY_ID)
    public CategoryDTO update(@PathVariable Long id, @RequestBody @Validated CategoryDTO categoryDTO) {
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping(URI_CATEGORY_ID)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}