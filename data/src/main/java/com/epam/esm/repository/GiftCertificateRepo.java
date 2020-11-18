package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepo extends CrudRepository<GiftCertificate, Long> {

    @Override
    List<GiftCertificate> findAll();
}
