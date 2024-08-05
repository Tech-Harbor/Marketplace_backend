package com.example.backend.utils.general;

import com.example.backend.utils.enums.RegisterAuthStatus;
import com.example.backend.utils.enums.Role;
import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import com.example.backend.web.Advertisement.store.mapper.AdvertisementMapper;
import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.File.ImageRepository;
import com.example.backend.web.File.store.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Named("BaseMapper")
@Component
@RequiredArgsConstructor
public class BaseMapper {

    private final AdvertisementMapper advertisementMapper;
    private final ImageRepository imageRepository;

    @Named("getAllAdvertisements")
    public List<AdvertisementDTO> getAllAdvertisements(List<AdvertisementEntity> advertisement) {
        return Collections.singletonList(advertisementMapper.advertisementMapperDTO
                ((AdvertisementEntity) advertisement)
        );
    }

    @Named("getAllImages")
    public List<ImageEntity> getAllImages(List<ImageEntity> image) {
        return imageRepository.findAll();
    }

    @Named("getCategoryName")
    public String getCategoryName(CategoryEntity category) {
        return category.getName();
    }

    @Named("getImageUrl")
    public String getImageUrl(ImageEntity image) {
        return image.getImageUrl();
    }

    @Named("getEnumRole")
    public Set<Role> getEnumRole(Set<Role> roles) {
        return roles;
    }

    @Named("getEnumRegisterAuthStatus")
    public RegisterAuthStatus getEnumRegisterAuthStatus(RegisterAuthStatus registerAuthStatus) {
        return registerAuthStatus;
    }
}
