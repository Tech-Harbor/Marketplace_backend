package com.example.backend.web.Advertisement;

import com.example.backend.utils.annotations.ApiResponseCreated;
import com.example.backend.utils.annotations.ApiResponseDelete;
import com.example.backend.utils.annotations.ApiResponseOK;
import com.example.backend.web.Advertisement.store.dto.AdvertisementCreateDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@Tag(name = "AdvertisementService")
@RequestMapping("/api")
public class AdvertisementController {

    private final AdvertisementServer advertisementServer;
    private static final String URL_CREATE = "/createAdvertisement";
    private static final String URL_EDIT = "/editAdvertisement";
    private static final String URL_DELETE = "/deleteAdvertisement";
    private static final String ADVERTISEMENT = "/advertisement";
    private static final String URL_DELETE_ALL = "/deleteAll";

    @Operation(summary = "Current user create advertisements")
    @ApiResponseCreated
    @PostMapping(value = URL_CREATE, consumes = {MULTIPART_FORM_DATA_VALUE}, produces = APPLICATION_JSON_VALUE)
    public AdvertisementCreateDTO createAdvertisementByUser(@RequestHeader(AUTHORIZATION) final String jwt,
                                                            @RequestPart final AdvertisementCreateDTO advertisement,
                                                            @RequestPart final List<MultipartFile> images) {
        return advertisementServer.createAdvertisement(jwt, advertisement, images);
    }

    @QueryMapping
    public List<AdvertisementDTO> getAllAdvertisement() {
        return advertisementServer.getAllAdvertisement();
    }

    @Operation(summary = "Current user get advertisement")
    @ApiResponseOK
    @GetMapping(value = ADVERTISEMENT, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public AdvertisementDTO getByAdvertisement(@RequestHeader(AUTHORIZATION) final String jwt) {
        return advertisementServer.advertisement(jwt);
    }

    @Operation(summary = "Current user update advertisement")
    @ApiResponseOK
    @PatchMapping(value = URL_EDIT, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public AdvertisementUpdateDTO editAdvertisement(@RequestHeader(AUTHORIZATION) final String jwt,
                                                    @RequestBody final AdvertisementUpdateDTO entity) {
        return advertisementServer.editAdvertisement(jwt, entity);
    }

    @Operation(summary = "Current user delete advertisement")
    @ApiResponseDelete
    @DeleteMapping(value = URL_DELETE, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void deleteAdvertisement(@RequestHeader(AUTHORIZATION) final String jwt) {
        advertisementServer.deleteAdvertisement(jwt);
    }

    @Operation(summary = "Current user will remove all advertisements")
    @ApiResponseDelete
    @DeleteMapping(value = URL_DELETE_ALL, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void deleteAllAdvertisement(@RequestHeader(AUTHORIZATION) final String jwt) {
        advertisementServer.deleteAll(jwt);
    }
}