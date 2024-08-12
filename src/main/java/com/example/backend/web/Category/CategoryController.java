package com.example.backend.web.Category;

import com.example.backend.utils.annotations.ApiResponseCreated;
import com.example.backend.utils.annotations.ApiResponseDelete;
import com.example.backend.utils.annotations.ApiResponseOK;
import com.example.backend.web.Category.store.dto.CategoryCreateDTO;
import com.example.backend.web.Category.store.dto.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@Tag(name = "CategoryService for Admin")
@RequestMapping("/api/admin")
public class CategoryController {

    private final CategoryServerImpl categoryService;
    private static final String URI_CATEGORIES_NAME = "/category/update";
    private static final String URI_CATEGORY = "/category";
    private static final String URI_CATEGORIES = "/categories";
    private static final String URI_CATEGORY_DELETE = "/category/delete";

    @Operation(summary = "GetAll categories for users")
    @QueryMapping
    @GetMapping(value = URI_CATEGORIES, consumes = APPLICATION_JSON_VALUE)
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getAll();
    }

    @QueryMapping
    public CategoryDTO getByNameCategory(@Argument final String name) {
        return categoryService.getCategoryDTOName(name);
    }

    @Operation(summary = "function create category for admin ")
    @ApiResponseCreated
    @PostMapping(value = URI_CATEGORY, consumes = {MULTIPART_FORM_DATA_VALUE}, produces = APPLICATION_JSON_VALUE)
    public CategoryCreateDTO create(@RequestPart @Validated final CategoryCreateDTO categoryDTO,
                                    @RequestPart final MultipartFile image) {
        return categoryService.create(categoryDTO, image);
    }

    @Operation(summary = "function update category for admin ")
    @ApiResponseOK
    @PatchMapping(value = URI_CATEGORIES_NAME, consumes = {MULTIPART_FORM_DATA_VALUE},
            produces = APPLICATION_JSON_VALUE)
    public CategoryCreateDTO update(@RequestParam final String name,
                                    @RequestPart @Validated final CategoryCreateDTO categoryDTO,
                                    @RequestPart final MultipartFile image) {
        return categoryService.update(name, categoryDTO, image);
    }

    @Operation(summary = "function delete category for admin")
    @ApiResponseDelete
    @DeleteMapping(value = URI_CATEGORY_DELETE, consumes = APPLICATION_JSON_VALUE)
    public void deleteCategory(@RequestBody final CategoryDTO categoryDTO) {
        categoryService.deleteCategory(categoryDTO);
    }
}