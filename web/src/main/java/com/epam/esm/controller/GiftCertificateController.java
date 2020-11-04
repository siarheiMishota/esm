package com.epam.esm.controller;

import static com.epam.esm.dao.StringParameters.PATTERN_KEY_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_NAME;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_SORT;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateMostChangeDto;
import com.epam.esm.entity.GiftCertificateParametersDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.service.GiftCertificateService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final GiftCertificateAdapter giftCertificateAdapter;

    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateAdapter giftCertificateAdapter) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateAdapter = giftCertificateAdapter;
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
        return giftCertificateAdapter.adaptListToListDto(giftCertificates);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable @Min(1) @NumberFormat long id) {
        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(id);
        if (optionalResult.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST,
                String.format("Requested resource not found (id=%d)", id));
        }
        return giftCertificateAdapter.adaptToDto(optionalResult.get());
    }

    @GetMapping("/tags/{id}")
    public List<GiftCertificateDto> getGiftCertificatesByTagId(@PathVariable long id) {
        return giftCertificateAdapter.adaptListToListDto(giftCertificateService.findByTagId(id));
    }

    @GetMapping("/tags")
    public List<GiftCertificateDto> getGiftCertificatesByTagIdWithEmptyTag() {
        return giftCertificateAdapter.adaptListToListDto(giftCertificateService.findAll());
    }

    @PostMapping
    public GiftCertificateDto createGiftCertificates(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateAdapter.adaptDtoTo(giftCertificateDto);

        giftCertificateService.add(giftCertificate);
        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(giftCertificate.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Gift certificate wasn't added");
        }
        return giftCertificateAdapter.adaptToDto(optionalResult.get());
    }

    @PutMapping
    public GiftCertificateDto updateGiftCertificate(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateAdapter.adaptDtoTo(giftCertificateDto);
        if (giftCertificateService.update(giftCertificate)) {
            return giftCertificateAdapter.adaptToDto(giftCertificate);
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
                return giftCertificateAdapter.adaptToDto(optionalResult.get());
            }
        }
        throw new ResourceException(HttpStatus.BAD_REQUEST, "Description and price of certificate wasn't updated");
    }
}
