package com.example.backend.Subcategory;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api")
public class SubcategoryController {

    private final SubcategoryServiceImpl subcategoryService;

    private static final String URI_SUBCATEGORIES = "/subcategories";

    private static final String URI_SUBCATEGORY_ID = "/subcategories/{id}";

    @GetMapping(URI_SUBCATEGORIES)
    public List<SubcategoryDTO> getAll() {
        return subcategoryService.getAll();
    }

    @GetMapping(URI_SUBCATEGORY_ID)
    public SubcategoryDTO getOneById(@PathVariable Long id) {
        return subcategoryService.getOneById(id);
    }

    @PostMapping(URI_SUBCATEGORIES)
    public SubcategoryDTO create(@RequestBody @Valid SubcategoryDTO subcategoryDTO) {
        return subcategoryService.create(subcategoryDTO);
    }

    @PutMapping(URI_SUBCATEGORY_ID)
    public SubcategoryDTO update(@PathVariable Long id, @RequestBody @Valid SubcategoryDTO subcategoryDTO) {
        return subcategoryService.update(id, subcategoryDTO);
    }

    @DeleteMapping(URI_SUBCATEGORY_ID)
    public void delete(@PathVariable Long id) {
        subcategoryService.delete(id);
    }
}