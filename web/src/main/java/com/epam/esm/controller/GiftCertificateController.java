package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public List<GiftCertificateDto> getGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateService.findAll(Map.of());
        return adaptToDto(giftCertificates);
    }

    private List<GiftCertificateDto> adaptToDto(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream()
                .map(giftCertificate -> new GiftCertificateDto(giftCertificate.getId(), giftCertificate.getName(),
                        giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getCreationDate(),
                        giftCertificate.getLastUpdateDate(), giftCertificate.getDuration(), giftCertificate.getTags()))
                .collect(Collectors.toList());
    }
}
