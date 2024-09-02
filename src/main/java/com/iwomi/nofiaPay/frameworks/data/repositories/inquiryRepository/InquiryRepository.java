package com.iwomi.nofiaPay.frameworks.data.repositories.inquiryRepository;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.mappers.IInquiryMapper;
import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.dtos.InquiryDto;
import com.iwomi.nofiaPay.frameworks.data.entities.EnrollEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.InquiryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InquiryRepository {

    private final IInquiryRepository repository;

    private final IInquiryMapper mapper;

    public List<InquiryEntity> getAllInquiries() {
        return repository.findAll();
    }

    public InquiryEntity createInquiry(InquiryDto dto) {
        InquiryEntity inquiry = mapper.mapToEntity(dto);
        return repository.save(inquiry);
    }

    public InquiryEntity getOne(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Inquiry not found."));
    }

    public InquiryEntity updateInquiry(UUID uuid, InquiryDto dto) {
        InquiryEntity entity = getOne(uuid);
        mapper.updateUserFromDto(dto, entity);
        return repository.save(entity);
    }

    public void deleteInquiry(UUID uuid) {
        repository.deleteById(uuid);
    }

}
