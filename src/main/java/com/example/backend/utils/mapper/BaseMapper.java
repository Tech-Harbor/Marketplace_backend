package com.example.backend.utils.mapper;

import com.example.backend.utils.enums.RegisterAuthStatus;
import com.example.backend.utils.enums.Role;
import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.File.ImageRepository;
import com.example.backend.web.File.store.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Named("BaseMapper")
@RequiredArgsConstructor
public class BaseMapper {

    private final ImageRepository imageRepository;

    @Named("getAllImages")
    public List<ImageEntity> getAllImages(final List<ImageEntity> image) {
        return imageRepository.findAll();
    }

    @Named("getCategoryName")
    public String getCategoryName(final CategoryEntity category) {
        return category.getName();
    }

    @Named("getImageUrl")
    public String getImageUrl(final ImageEntity image) {
        return image.getImageUrl();
    }

    @Named("getEnumRole")
    public Set<Role> getEnumRole(final Set<Role> roles) {
        return roles;
    }

    @Named("getEnumRegisterAuthStatus")
    public RegisterAuthStatus getEnumRegisterAuthStatus(final RegisterAuthStatus registerAuthStatus) {
        return registerAuthStatus;
    }
}
