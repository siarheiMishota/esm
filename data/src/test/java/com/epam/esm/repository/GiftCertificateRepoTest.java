package com.epam.esm.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.esm.entity.GiftCertificate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class GiftCertificateRepoTest {

    @Autowired
    private GiftCertificateRepo giftCertificateRepo;

    @Test
    public void findAll() {
        Optional<GiftCertificate> byId = giftCertificateRepo.findById(33L);
        assertEquals(Optional.empty(), byId);
    }
}