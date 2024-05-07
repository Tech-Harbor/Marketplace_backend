package com.example.backend.web.Advertisement;

import com.example.backend.web.Category.CategoryEntity;
import com.example.backend.web.Category.CategoryService;
import com.example.backend.web.User.UserService;
import com.example.backend.web.User.store.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
        final UserEntity userId = userService.getById(id);
        final CategoryEntity categoryId = categoryService.getById(advertisement.categoryId());

        final AdvertisementEntity newAdvertisement = AdvertisementEntity.builder()
                .id(advertisement.id())
                .user(userId)
                .name(advertisement.name())
                .characteristicAdvertisement(advertisement.characteristicAdvertisement())
                .descriptionAdvertisement(advertisement.descriptionAdvertisement())
                .price(advertisement.price())
                .images(advertisement.images())
                .location(advertisement.location())
                .createDate(LocalDateTime.now())
                .category(categoryId)
                .build();

        return advertisementFactory.makeAdvertisement(advertisementRepository.save(newAdvertisement));
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisement() {
        return advertisementRepository.findAll().stream()
                .map(advertisementFactory::makeAdvertisement)
                .collect(Collectors.toList());
    }

    @Override
    public AdvertisementDTO getOneAdvertisement(final Long id) {
        final AdvertisementEntity entityId = getIdAdvertisement(id);

        return advertisementFactory.makeAdvertisement(entityId);
    }

    @Override
    public AdvertisementDTO editAdvertisement(final Long id, final AdvertisementDTO entity) {
        final AdvertisementEntity entityId = getIdAdvertisement(id);

            entityId.setName(entity.name());
            entityId.setCharacteristicAdvertisement(entity.characteristicAdvertisement());
            entityId.setDescriptionAdvertisement(entity.descriptionAdvertisement());
            entityId.setCreateDate(entity.createDate());
            entityId.setPrice(entity.price());

        return advertisementFactory.makeAdvertisement(advertisementRepository.save(entityId));
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

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<AdvertisementEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertisementEntity.class);

        Root<AdvertisementEntity> advertisementEntityRoot = criteriaQuery.from(AdvertisementEntity.class);

        Predicate nameAdvertisementPredicate = criteriaBuilder.equal(advertisementEntityRoot.get("name"), name);

        criteriaQuery.where(nameAdvertisementPredicate);

        TypedQuery<AdvertisementEntity> typedQuery = em.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private AdvertisementEntity getIdAdvertisement(final Long id) {
        return advertisementRepository.getReferenceById(id);
    }
}
