package com.example.backend.web.Advertisement;

import com.example.backend.utils.general.Helpers;
import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import com.example.backend.web.Advertisement.store.dto.AdvertisementCreateDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementUpdateDTO;
import com.example.backend.web.Advertisement.store.factory.AdvertisementCreateFactory;
import com.example.backend.web.Advertisement.store.factory.AdvertisementFactory;
import com.example.backend.web.Advertisement.store.factory.AdvertisementUpdateFactory;
import com.example.backend.web.Category.CategoryService;
import com.example.backend.web.File.ImageService;
import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.User.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementUpdateFactory advertisementUpdateFactory;
    private final AdvertisementCreateFactory advertisementCreateFactory;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementFactory advertisementFactory;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final UserService userService;
    private final Helpers helpers;

    @Override
    @Transactional
    public AdvertisementCreateDTO createAdvertisement(final String jwt,
                                                      final AdvertisementCreateDTO advertisement,
                                                      final List<MultipartFile> files) {
        final var user = helpers.tokenUserData(jwt);
        final var userName = userService.getByUserFirstName(user.getFirstname());
        final var categoryName = categoryService.getCategoryName(advertisement.category());
        final var imagesList = new ArrayList<ImageEntity>();

        for (MultipartFile file : files) {
            imagesList.add(imageService.uploadImageEntity(file));
        }

        final var newAdvertisement = AdvertisementEntity.builder()
                .user(userName)
                .name(advertisement.name())
                .descriptionAdvertisement(advertisement.descriptionAdvertisement())
                .price(advertisement.price())
                .images(imagesList)
                .createDate(LocalDateTime.now())
                .updateActiveDate(LocalDateTime.now())
                .category(categoryName)
                .delivery(advertisement.delivery())
                .auction(advertisement.auction())
                .active(advertisement.active())
                .build();

        return advertisementCreateFactory.apply(advertisementRepository.save(newAdvertisement));
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisement() {
        return advertisementRepository.findAll().stream()
                .map(advertisementFactory)
                .toList();
    }

    @Override
    public AdvertisementDTO advertisement(final String jwt) {
        final var user = helpers.tokenUserData(jwt);
        final var advertisementRepositoryByName =
                advertisementRepository.getByName(user.getAdvertisements().get(0).getName());

        return advertisementFactory.apply(advertisementRepositoryByName);
    }

    @Override
    @Transactional
    public AdvertisementUpdateDTO editAdvertisement(final String jwt, final AdvertisementUpdateDTO advertisementDTO) {
        final var user = helpers.tokenUserData(jwt);
        final var idAdvertisement =
                advertisementRepository.getReferenceById(user.getAdvertisements().get(0).getId());
        final var auctionParse = String.valueOf(advertisementDTO.auction());
        final var activeParse = String.valueOf(advertisementDTO.active());

        if (StringUtils.isNoneEmpty(advertisementDTO.name())) {
            idAdvertisement.setName(advertisementDTO.name());
        }

        if (StringUtils.isNoneEmpty(advertisementDTO.characteristicAdvertisement())) {
            idAdvertisement.setCharacteristicAdvertisement(advertisementDTO.characteristicAdvertisement());
        }

        if (StringUtils.isNoneEmpty(advertisementDTO.descriptionAdvertisement())) {
            idAdvertisement.setDescriptionAdvertisement(advertisementDTO.descriptionAdvertisement());
        }

        if (StringUtils.isNoneEmpty(advertisementDTO.delivery().toString())) {
            idAdvertisement.setDelivery(advertisementDTO.delivery());
        }

        if (StringUtils.isNoneEmpty(advertisementDTO.location())) {
            idAdvertisement.setLocation(advertisementDTO.location());
        }

        if (StringUtils.isNoneEmpty(advertisementDTO.price().toString())) {
            idAdvertisement.setPrice(advertisementDTO.price());
        }

        if (StringUtils.isNoneEmpty(auctionParse)) {
            idAdvertisement.setAuction(advertisementDTO.auction());
        }

        if (StringUtils.isNoneEmpty(activeParse)) {
            idAdvertisement.setAuction(advertisementDTO.active());
        }

        return advertisementUpdateFactory.apply(advertisementRepository.save(idAdvertisement));
    }

    @Override
    @Transactional
    public void deleteAdvertisement(final String jwt) {
        final var user = helpers.tokenUserData(jwt);
        final var advertisementRepositoryByName =
                advertisementRepository.getByName(user.getAdvertisements().get(0).getName());

        advertisementRepository.delete(advertisementRepositoryByName);
    }

    @Override
    @Transactional
    public void deleteAll(final String jwt) {
        helpers.tokenUserData(jwt);
        advertisementRepository.deleteAll();
    }
}