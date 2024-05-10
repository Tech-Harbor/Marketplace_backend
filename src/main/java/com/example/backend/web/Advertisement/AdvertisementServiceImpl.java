package com.example.backend.web.Advertisement;

import com.example.backend.web.Category.CategoryService;
import com.example.backend.web.User.UserService;
import jakarta.persistence.EntityManager;
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
    private final EntityManager em;

    @Override
    @Transactional
    public AdvertisementDTO createAdvertisement(final Long id, final AdvertisementDTO advertisement) {
        final var userId = userService.getById(id);
        final var categoryName = categoryService.getByCategoryName(advertisement.category());

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

    @Override
    public List<AdvertisementEntity> getFilterAdvertisementName(final String name) {
        final var criteriaBuilder = em.getCriteriaBuilder();

        final var criteriaQuery = criteriaBuilder.createQuery(AdvertisementEntity.class);

        final var advertisementEntityRoot = criteriaQuery.from(AdvertisementEntity.class);

        final var nameAdvertisementPredicate = criteriaBuilder.equal(
                    advertisementEntityRoot.get("name"), name
                );

        final var typedQuery = em.createQuery(criteriaQuery);

        criteriaQuery.where(nameAdvertisementPredicate);

        return typedQuery.getResultList();
    }

    private AdvertisementEntity getIdAdvertisement(final Long id) {
        return advertisementRepository.getReferenceById(id);
    }
}
