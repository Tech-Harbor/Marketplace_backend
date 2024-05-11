package com.example.backend.web.Advertisement;

import com.example.backend.web.Category.CategoryService;
import com.example.backend.web.User.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementFactory advertisementFactory;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    @Transactional
    public AdvertisementDTO createAdvertisement(final Long id, final AdvertisementDTO advertisement) {
        final var userId = userService.getById(id);
        final var categoryName = categoryService.getCategoryName(advertisement.category());

        final var newAdvertisement = AdvertisementEntity.builder()
                .id(advertisement.id())
                .user(userId)
                .name(advertisement.name())
                .characteristicAdvertisement(advertisement.characteristicAdvertisement())
                .descriptionAdvertisement(advertisement.descriptionAdvertisement())
                .price(advertisement.price())
                .images(advertisement.images())
                .location(advertisement.location())
                .createDate(LocalDateTime.now())
                .category(categoryName)
                .build();

        return advertisementFactory.apply(advertisementRepository.save(newAdvertisement));
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisement() {
        return advertisementRepository.findAll().stream()
                .map(advertisementFactory)
                .collect(Collectors.toList());
    }

    @Override
    public AdvertisementDTO getOneAdvertisement(final Long id) {
        final var entityId = getIdAdvertisement(id);

        return advertisementFactory.apply(entityId);
    }

    @Override
    public AdvertisementDTO editAdvertisement(final Long id, final AdvertisementDTO entity) {
        final var entityId = getIdAdvertisement(id);

            entityId.setName(entity.name());
            entityId.setCharacteristicAdvertisement(entity.characteristicAdvertisement());
            entityId.setDescriptionAdvertisement(entity.descriptionAdvertisement());
            entityId.setCreateDate(entity.createDate());
            entityId.setPrice(entity.price());

        return advertisementFactory.apply(advertisementRepository.save(entityId));
    }

    @Override
    public void deleteIdAdvertisement(final Long id) {
        advertisementRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        advertisementRepository.deleteAll();
    }

    private AdvertisementEntity getIdAdvertisement(final Long id) {
        return advertisementRepository.getReferenceById(id);
    }
}
