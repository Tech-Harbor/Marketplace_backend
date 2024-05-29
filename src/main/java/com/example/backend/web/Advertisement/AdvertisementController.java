package com.example.backend.web.Advertisement;

import com.example.backend.utils.annotations.ApiResponseCreated;
import com.example.backend.utils.annotations.ApiResponseDelete;
import com.example.backend.web.Advertisement.store.dto.AdvertisementCreateDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@Tag(name = "AdvertisementService")
@RequestMapping("/api")
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private static final String URL_CREATE = "/createAdvertisement";
    private static final String URL_EDIT = "/editAdvertisement";
    private static final String URL_DELETE = "/deleteAdvertisement";
    public static final String ADVERTISEMENT = "/advertisement";
    private static final String URL_DELETE_ALL = "/deleteAll";

    @PostMapping(value = URL_CREATE, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponseCreated
    public AdvertisementCreateDTO createAdvertisementByUser(@RequestHeader(AUTHORIZATION) final String jwt,
                                                            @RequestPart final AdvertisementCreateDTO advertisement,
                                                            @RequestPart final List<MultipartFile> images) {
        return advertisementService.createAdvertisement(jwt, advertisement, images);
    }

    @QueryMapping
    public List<AdvertisementDTO> getAllAdvertisement() {
        return advertisementService.getAllAdvertisement();
    }

    @GetMapping(ADVERTISEMENT)
    public AdvertisementDTO getByAdvertisement(@RequestHeader(AUTHORIZATION) final String jwt) {
        return advertisementService.advertisement(jwt);
    }

    @PatchMapping(URL_EDIT)
    public AdvertisementDTO editAdvertisement(@RequestHeader(AUTHORIZATION) final String jwt,
                                              @RequestBody final AdvertisementDTO entity) {
        return advertisementService.editAdvertisement(jwt, entity);
    }

    @DeleteMapping(URL_DELETE)
    @ApiResponseDelete
    public void deleteAdvertisement(@RequestHeader(AUTHORIZATION) final String jwt) {
        advertisementService.deleteAdvertisement(jwt);
    }

    @DeleteMapping(URL_DELETE_ALL)
    @ApiResponseDelete
    public void deleteAllAdvertisement() {
        advertisementService.deleteAll();
    }
}