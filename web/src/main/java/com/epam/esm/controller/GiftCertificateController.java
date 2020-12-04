package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateParameter;
import com.epam.esm.entity.GiftCertificateParameterDto;
import com.epam.esm.entity.GiftCertificatePatchDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateUtil;
import com.epam.esm.util.converter.GiftCertificateConverter;
import com.epam.esm.util.converter.GiftCertificateParameterConverter;
import com.epam.esm.util.converter.PaginationConverter;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final GiftCertificateConverter giftCertificateConverter;
    private final GiftCertificateUtil giftCertificateUtil;
    private final PaginationConverter paginationConverter;
    private final GiftCertificateParameterConverter giftCertificateParameterConverter;

    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateConverter giftCertificateConverter,
                                     GiftCertificateUtil giftCertificateUtil,
                                     PaginationConverter paginationConverter,
                                     GiftCertificateParameterConverter giftCertificateParameterConverter) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateConverter = giftCertificateConverter;
        this.giftCertificateUtil = giftCertificateUtil;
        this.paginationConverter = paginationConverter;
        this.giftCertificateParameterConverter = giftCertificateParameterConverter;
    }

    @GetMapping()
    public CollectionModel<GiftCertificateDto> getGiftCertificates(@Valid PaginationDto paginationDto,
                                                                   @Valid GiftCertificateParameterDto giftCertificateParameterDto) {
        Pagination pagination = paginationConverter.convertFromDto(paginationDto);
        GiftCertificateParameter giftCertificateParameter = giftCertificateParameterConverter.convertFromDto(
            giftCertificateParameterDto);

        List<GiftCertificate> giftCertificates = giftCertificateService.findAll(giftCertificateParameter, pagination);
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found ", CodeOfEntity.GIFT_CERTIFICATE);
        }
        List<GiftCertificateDto> giftCertificateDtos = giftCertificateConverter.convertListToListDto(giftCertificates);
        for (GiftCertificateDto giftCertificateDto : giftCertificateDtos) {
            addLinksInDto(giftCertificateDto);
        }
        return CollectionModel.of(giftCertificateDtos);
    }

    @GetMapping("/{id}")
    public EntityModel<GiftCertificateDto> getGiftCertificateById(@PathVariable Long id) {
        if (id < 0) {
            throw new ResourceException(String.format("Id is negative (id=%d)", id), CodeOfEntity.GIFT_CERTIFICATE);
        }

        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (id=%d)", id), CodeOfEntity.GIFT_CERTIFICATE);
        }
        GiftCertificateDto giftCertificateDto = giftCertificateConverter.convertToDto(optionalResult.get());
        addLinksInDto(giftCertificateDto);
        return EntityModel.of(giftCertificateDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public EntityModel<GiftCertificateDto> createGiftCertificates(
        @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateConverter.convertFromDto(giftCertificateDto);

        giftCertificateService.add(giftCertificate);
        Optional<GiftCertificate> optionalResult = giftCertificateService.findById(giftCertificate.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException("Gift certificate wasn't added", CodeOfEntity.GIFT_CERTIFICATE);
        }
        GiftCertificateDto giftCertificateResultDto = giftCertificateConverter.convertToDto(optionalResult.get());
        addLinksInDto(giftCertificateResultDto);
        return EntityModel.of(giftCertificateResultDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public EntityModel<GiftCertificateDto> updateGiftCertificate(@PathVariable long id,
                                                                 @RequestBody
                                                                 @Valid GiftCertificateDto giftCertificateDto) {
        if (id < 0) {
            throw new ResourceNotFoundException(
                "Gift certificate wasn't updated because id is negative", CodeOfEntity.GIFT_CERTIFICATE);
        }
        giftCertificateDto.setId(id);

        GiftCertificate giftCertificate = giftCertificateConverter.convertFromDto(giftCertificateDto);
        if (!giftCertificateService.update(giftCertificate)) {
            throw new ResourceException("Gift certificate wasn't updated", CodeOfEntity.GIFT_CERTIFICATE);
        }
        GiftCertificateDto giftCertificateResultDto = giftCertificateConverter.convertToDto(giftCertificate);
        addLinksInDto(giftCertificateResultDto);
        return EntityModel.of(giftCertificateResultDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public EntityModel<GiftCertificateDto> updatePartGiftCertificate(
        @RequestBody GiftCertificatePatchDto giftCertificatePatchDto, @PathVariable long id) {

        if (id < 0) {
            throw new ResourceNotFoundException(
                "Part of gift certificate wasn't updated because id is negative", CodeOfEntity.GIFT_CERTIFICATE);
        }

        giftCertificatePatchDto.setId(id);

        GiftCertificate giftCertificate = giftCertificateUtil.buildNotNullFieldInGiftCertificate(
            giftCertificatePatchDto);

        if (!giftCertificateService.update(giftCertificate)) {
            throw new ResourceException("Gift certificate wasn't updated from patch", CodeOfEntity.GIFT_CERTIFICATE);
        }

        GiftCertificateDto giftCertificateResultDto = giftCertificateConverter.convertToDto(giftCertificate);
        addLinksInDto(giftCertificateResultDto);
        return EntityModel.of(giftCertificateResultDto);
    }

    private void addLinksInDto(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificateById(
            giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.getTags().forEach(tagDto ->
            tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel()));
    }
}
