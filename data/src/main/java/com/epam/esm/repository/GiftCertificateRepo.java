package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepo extends JpaRepository<GiftCertificate, Long> {

    @Override
    List<GiftCertificate> findAll();

    @Override
    Optional<GiftCertificate> findById(Long aLong);

}
