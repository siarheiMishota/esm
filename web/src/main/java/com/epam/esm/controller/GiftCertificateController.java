package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
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
        parameterMap.put("name", name);
        parameterMap.put("description", description);
        parameterMap.put("sort", sort);

        Map<String, String> validatedMap = giftCertificateService.validate(parameterMap);
        List<GiftCertificate> giftCertificates;

        if (validatedMap.isEmpty()) {
            giftCertificates = giftCertificateService.findAll();
        } else {
            giftCertificates = giftCertificateService.findAll(validatedMap);
        }
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
