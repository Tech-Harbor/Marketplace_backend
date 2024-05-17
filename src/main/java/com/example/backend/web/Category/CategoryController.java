package com.example.backend.web.Category;

import com.example.backend.web.Category.store.dto.CategoryCreateDTO;
import com.example.backend.web.Category.store.dto.CategoryDTO;
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
    public CategoryCreateDTO create(@RequestBody @Validated final CategoryCreateDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @PutMapping(URI_CATEGORIES_ID)
    public CategoryCreateDTO update(@PathVariable final Long id,
                                    @RequestBody @Validated final CategoryCreateDTO categoryDTO) {
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping(URI_CATEGORIES_ID)
    public void delete(@PathVariable final Long id) {
        categoryService.deleteId(id);
    }
}