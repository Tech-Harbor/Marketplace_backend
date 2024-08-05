package com.example.backend.web.Category;

import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.Category.store.dto.CategoryCreateDTO;
import com.example.backend.web.Category.store.dto.CategoryDTO;
import com.example.backend.web.Category.store.mapper.CategoryMapper;
import com.example.backend.web.File.ImageServer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServerImpl implements CategoryServer {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ImageServer imageServer;

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryDTO)
                .toList();
    }

    @Override
    public CategoryEntity getCategoryName(final String name) {
        return categoryRepository.getByName(name);
    }

    @Override
    public CategoryDTO getCategoryDTOName(final String name) {
        return categoryMapper.categoryDTO(categoryRepository.getByName(name));
    }

    @Override
    @Transactional
    public CategoryCreateDTO create(final CategoryCreateDTO categoryDTO, final MultipartFile image) {
        final var newImage = imageServer.uploadImageEntity(image);

        final var newCategory = CategoryEntity.builder()
                .name(categoryDTO.name())
                .image(newImage)
                .build();

        return categoryMapper.categoryMapperCreateDTO(categoryRepository.save(newCategory));
    }

    @Override
    @Transactional
    public CategoryCreateDTO update(final String name,
                                    final CategoryCreateDTO categoryDTO,
                                    final MultipartFile image) {
        final var category = getCategoryName(name);
        final var uploadImage = imageServer.uploadImageEntity(image);

        if (StringUtils.isNoneEmpty(categoryDTO.name())) {
            category.setName(categoryDTO.name());
        }

        if (StringUtils.isNoneEmpty(categoryDTO.image())) {
            category.setImage(uploadImage);
        }

        return categoryMapper.categoryMapperCreateDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(final CategoryDTO categoryDTO) {
        final var deleteName = getCategoryName(categoryDTO.name());

        categoryRepository.delete(deleteName);
    }
}