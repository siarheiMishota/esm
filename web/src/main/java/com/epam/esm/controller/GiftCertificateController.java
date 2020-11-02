package com.epam.esm.controller;

import static com.epam.esm.dao.StringParameters.PATTERN_KEY_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_NAME;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_SORT;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateMostChangeDto;
import com.epam.esm.entity.GiftCertificateParametersDto;
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
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public List<GiftCertificateDto> getGiftCertificates(@Valid GiftCertificateParametersDto giftCertificateParametersDto) {
        Map<String, String> parameterMap = new HashMap<>();
        if (giftCertificateParametersDto.getName() != null) {
            parameterMap.put(PATTERN_KEY_NAME, giftCertificateParametersDto.getName());
        }
        if (giftCertificateParametersDto.getDescription() != null) {
            parameterMap.put(PATTERN_KEY_DESCRIPTION, giftCertificateParametersDto.getDescription());
        }
        if (giftCertificateParametersDto.getSort() != null) {
            parameterMap.put(PATTERN_KEY_SORT, giftCertificateParametersDto.getSort());
        }

        List<GiftCertificate> giftCertificates;
        if (parameterMap.isEmpty()) {
            giftCertificates = giftCertificateService.findAll();
        } else {
            giftCertificates = giftCertificateService.findAll(parameterMap);
        }
        return adaptListToListDto(giftCertificates);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable @Min(1) @NumberFormat long id) {
        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(id);
        if (optionalResult.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST,
                String.format("Requested resource not found (id=%d)", id));
        }
        return adaptToDto(optionalResult.get());
    }

    @GetMapping("/tags/{id}")
    public List<GiftCertificateDto> getGiftCertificatesByTagName(@PathVariable long id) {
        return adaptListToListDto(giftCertificateService.findByTagId(id));
    }

    @GetMapping("/tags")
    public List<GiftCertificateDto> getGiftCertificatesByTagNameWithEmptyTag() {
        return adaptListToListDto(giftCertificateService.findAll());
    }

    @PostMapping
    public GiftCertificateDto createGiftCertificates(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = adaptDtoTo(giftCertificateDto);

        giftCertificateService.add(giftCertificate);
        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(giftCertificate.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Gift certificate wasn't added");
        }
        return adaptToDto(optionalResult.get());
    }

    @PutMapping
    public GiftCertificateDto updateGiftCertificate(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = adaptDtoTo(giftCertificateDto);
        if (giftCertificateService.update(giftCertificate)) {
            return adaptToDto(giftCertificate);
        } else {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Gift certificate wasn't updated");
        }
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteGiftCertificate(@PathVariable @Valid @Min(0) long id) {
        giftCertificateService.delete(id);
        return HttpStatus.OK;
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto updatePartGiftCertificate(
        @RequestBody GiftCertificateMostChangeDto giftCertificateMostChangeDto, @PathVariable long id) {
        if (giftCertificateService.updateDescriptionAndPrice(id, giftCertificateMostChangeDto.getDescription(),
            giftCertificateMostChangeDto.getPrice())) {
            Optional<GiftCertificate> optionalResult = giftCertificateService.findById(id);
            if (optionalResult.isPresent()) {
                return adaptToDto(optionalResult.get());
            }
        }
        throw new ResourceException(HttpStatus.BAD_REQUEST, "Description and price ift certificate wasn't updated");
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
