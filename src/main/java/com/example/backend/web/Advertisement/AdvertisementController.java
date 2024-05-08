package com.example.backend.web.Advertisement;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisement")
public class AdvertisementController {

    private final AdvertisementServiceImpl advertisementService;
    private static final String URI_ADVERTISEMENT_ID = "/{id}";
    private static final String URI_ADVERTISEMENT_FILTER = "/filter/{name}";
    private static final String URL_DELETE_ALL = "/deleteAll";

    @PostMapping(URI_ADVERTISEMENT_ID)
    @MutationMapping
    public AdvertisementDTO createAdvertisementIdByUser(@PathVariable(value = "id") @Argument final Long userId,
                                                        @RequestBody @Argument final AdvertisementDTO entity) {
        return advertisementService.createAdvertisement(userId, entity);
    }

    @QueryMapping
    public List<AdvertisementDTO> getAllAdvertisement() {
        return advertisementService.getAllAdvertisement();
    }

    @QueryMapping
    public AdvertisementDTO getByIdAdvertisement(@Argument final Long id) {
        return advertisementService.getOneAdvertisement(id);
    }

    @PutMapping(URI_ADVERTISEMENT_ID)
    public AdvertisementDTO editAdvertisement(@PathVariable final Long id, @RequestBody final AdvertisementDTO entity) {
        return advertisementService.editAdvertisement(id, entity);
    }

    @DeleteMapping(URI_ADVERTISEMENT_ID)
    public void deleteIdAdvertisement(@PathVariable final Long id) {
        advertisementService.deleteIdAdvertisement(id);
    }

    @GetMapping(URI_ADVERTISEMENT_FILTER)
    public List<AdvertisementEntity> getFilterName(@PathVariable final String name) {
        return advertisementService.getFilterAdvertisementName(name);
    }

    @DeleteMapping(URL_DELETE_ALL)
    public void deleteAllAdvertisement() {
        advertisementService.deleteAll();
    }
}