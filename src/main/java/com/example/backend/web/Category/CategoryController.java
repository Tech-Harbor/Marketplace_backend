package com.example.backend.web.Category;

import com.example.backend.utils.annotations.ApiResponseCreated;
import com.example.backend.utils.annotations.ApiResponseDelete;
import com.example.backend.utils.annotations.ApiResponseOK;
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
@RequestMapping("/api")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private static final String URI_CATEGORIES_ID = "/category/{id}";
    private static final String URI_CATEGORY = "/category";
    private static final String URI_CATEGORIES = "/categories";
    private static final String URI_CATEGORY_DELETE = "/category/delete";

    @GetMapping(URI_CATEGORIES)
    @QueryMapping
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getAll();
    }

    @QueryMapping
    public CategoryDTO getByNameCategory(@Argument final String name) {
        return categoryService.getCategoryDTOName(name);
    }

    @PostMapping(URI_CATEGORY)
    @ApiResponseCreated
    public CategoryCreateDTO create(@RequestBody @Validated final CategoryCreateDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @PutMapping(URI_CATEGORIES_ID)
    @ApiResponseOK
    public CategoryCreateDTO update(@PathVariable final Long id,
                                    @RequestBody @Validated final CategoryCreateDTO categoryDTO) {
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping(URI_CATEGORY_DELETE)
    @ApiResponseDelete
    public void deleteCategory(@RequestBody final CategoryDTO categoryDTO) {
        categoryService.deleteCategory(categoryDTO);
    }
}