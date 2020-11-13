package com.epam.esm.controller;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateParametersDto;
import com.epam.esm.entity.GiftCertificatePatchDto;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateUtil;
import com.epam.esm.util.PaginationUtil;
import com.epam.esm.util.adapter.GiftCertificateAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.NumberFormat;
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
    private final GiftCertificateUtil giftCertificateUtil;
    private final PaginationUtil paginationUtil;

    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateAdapter giftCertificateAdapter,
                                     GiftCertificateUtil giftCertificateUtil,
                                     PaginationUtil paginationUtil) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateAdapter = giftCertificateAdapter;
        this.giftCertificateUtil = giftCertificateUtil;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping()
    public List<GiftCertificateDto> getGiftCertificates(@Valid PaginationDto paginationDto,
                                                        @Valid GiftCertificateParametersDto giftCertificateParametersDto) {
        Map<String, String> parameterMap = new HashMap<>();
        giftCertificateUtil.fillInMapFromPaginationDto(giftCertificateParametersDto,parameterMap);
        paginationUtil.fillInMapFromPaginationDto(paginationDto,parameterMap);

        List<GiftCertificate> giftCertificates;
        if (parameterMap.isEmpty()) {
            giftCertificates = giftCertificateService.findAll();
        } else {
            giftCertificates = giftCertificateService.findAll(parameterMap);
            if (giftCertificates.isEmpty()) {
                throw new ResourceNotFoundException("Requested resource not found ", CodeOfEntity.GIFT_CERTIFICATE);
            }
        }
        return giftCertificateAdapter.adaptListToListDto(giftCertificates);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable @NumberFormat @Min(0) Long id) {
        if (id < 0) {
            throw new ResourceException(String.format("Id is negative (id=%d)", id), CodeOfEntity.GIFT_CERTIFICATE);
        }

        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (id=%d)", id), CodeOfEntity.GIFT_CERTIFICATE);
        }
        return giftCertificateAdapter.adaptToDto(optionalResult.get());
    }

    @PostMapping
    public GiftCertificateDto createGiftCertificates(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateAdapter.adaptDtoTo(giftCertificateDto);

        giftCertificateService.add(giftCertificate);
        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(giftCertificate.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException("Gift certificate wasn't added", CodeOfEntity.GIFT_CERTIFICATE);
        }
        return giftCertificateAdapter.adaptToDto(optionalResult.get());
    }

    @PutMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        if (id < 0) {
            throw new ResourceNotFoundException(
                "Gift certificate wasn't updated because id is negative", CodeOfEntity.GIFT_CERTIFICATE);
        }
        giftCertificateDto.setId(id);

        GiftCertificate giftCertificate = giftCertificateAdapter.adaptDtoTo(giftCertificateDto);
        if (giftCertificateService.update(giftCertificate)) {
            return giftCertificateAdapter.adaptToDto(giftCertificate);
        } else {
            throw new ResourceException("Gift certificate wasn't updated", CodeOfEntity.GIFT_CERTIFICATE);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException(
                "Gift certificate wasn't deleted because id is negative", CodeOfEntity.GIFT_CERTIFICATE);
        }

        if (giftCertificateService.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(String.format("Id= %d is not exist", id),
                CodeOfEntity.GIFT_CERTIFICATE);
        }
        giftCertificateService.delete(id);
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto updatePartGiftCertificate(
        @RequestBody GiftCertificatePatchDto giftCertificatePatchDto, @PathVariable long id) {
        if (id < 0) {
            throw new ResourceNotFoundException(
                "Part of gift certificate wasn't updated because id is negative", CodeOfEntity.GIFT_CERTIFICATE);
        }
        giftCertificatePatchDto.setId(id);

        GiftCertificate giftCertificate = giftCertificateUtil.fillNotNullFieldInGiftCertificate(
            giftCertificatePatchDto);

        if (giftCertificateService.update(giftCertificate)) {
            return giftCertificateAdapter.adaptToDto(giftCertificate);
        }
        throw new ResourceException("Gift certificate wasn't updated from patch", CodeOfEntity.GIFT_CERTIFICATE);
    }
}
