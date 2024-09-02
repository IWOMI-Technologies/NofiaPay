package com.iwomi.nofiaPay.frameworks.data.repositories.inquiryRepository;

import com.iwomi.nofiaPay.frameworks.data.entities.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IInquiryRepository extends JpaRepository<InquiryEntity, UUID> {

}
