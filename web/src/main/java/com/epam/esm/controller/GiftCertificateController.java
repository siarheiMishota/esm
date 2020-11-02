package com.epam.esm.controller;

import static com.epam.esm.dao.StringParameters.PATTERN_KEY_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_NAME;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_SORT;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.service.GiftCertificateService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public List<GiftCertificateDto> getGiftCertificates(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String description,
                                                        @RequestParam(required = false) String sort) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(PATTERN_KEY_NAME, name);
        parameterMap.put(PATTERN_KEY_DESCRIPTION, description);
        parameterMap.put(PATTERN_KEY_SORT, sort);

        Map<String, String> validatedMap = giftCertificateService.validateRequestLine(parameterMap);
        List<GiftCertificate> giftCertificates;

        if (validatedMap.isEmpty()) {
            giftCertificates = giftCertificateService.findAll();
        } else {
            giftCertificates = giftCertificateService.findAll(validatedMap);
        }
        return adaptListToListDto(giftCertificates);
    }

    @GetMapping("/tags/{tagName}")
    public List<GiftCertificateDto> getGiftCertificatesByTagName(@PathVariable String tagName) {
        return adaptListToListDto(giftCertificateService.findByTagName(tagName));
    }

    @GetMapping("/tags")
    public List<GiftCertificateDto> getGiftCertificatesByTagNameWithEmptyTag() {
        return adaptListToListDto(giftCertificateService.findAll());
    }

    @PostMapping
    public GiftCertificateDto createGiftCertificates(@RequestBody GiftCertificateDto giftCertificateDto) {
        if (notValidateCreationAndLasUpdateDate(giftCertificateDto)) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "date isn't correct");
        }

        GiftCertificate giftCertificate = adaptDtoTo(giftCertificateDto);
        Map<String, String> incorrectMap = giftCertificateService.validateCreating(giftCertificate);

        if (!incorrectMap.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, createMessageException(incorrectMap));
        }
        giftCertificateService.add(giftCertificate);
        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(giftCertificate.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Gift certificate wasn't added");
        }
        return adaptToDto(optionalResult.get());
    }

    private String createMessageException(Map<String, String> incorrectMap) {
        return incorrectMap.entrySet()
            .stream()
            .map(entry -> new StringBuilder().append(entry.getKey()).append(": ").append(entry.getValue()))
            .collect(Collectors.joining(","));
    }

    private boolean notValidateCreationAndLasUpdateDate(GiftCertificateDto giftCertificateDto) {
        return !giftCertificateService.validateDate(giftCertificateDto.getCreationDate())
            || !giftCertificateService.validateDate(giftCertificateDto.getLastUpdateDate());
    }

    private GiftCertificate adaptDtoTo(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setCreationDate(LocalDateTime.parse(giftCertificateDto.getCreationDate()));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse(giftCertificateDto.getLastUpdateDate()));
        giftCertificate.setDuration(giftCertificateDto.getDuration());

        List<Tag> tagsDto = giftCertificateDto.getTags();
        if (tagsDto == null) {
            giftCertificate.setTags(new ArrayList<>());
        } else {
            giftCertificate.setTags(tagsDto.stream()
                .map(tagDto -> new Tag(tagDto.getId(), tagDto.getName()))
                .collect(Collectors.toList()));
        }
        return giftCertificate;
    }

    private GiftCertificateDto adaptToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setCreationDate(giftCertificate.getCreationDate().toString());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate().toString());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setTags(giftCertificate.getTags());
        return giftCertificateDto;
    }

    private List<GiftCertificateDto> adaptListToListDto(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream()
            .map(this::adaptToDto)
            .collect(Collectors.toList());
    }
}
