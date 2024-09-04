package com.iwomi.nofiaPay.frameworks.data.repositories.inquiryRepository;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.mappers.IInquiryMapper;
import com.iwomi.nofiaPay.core.mappers.IReplyMapper;
import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.dtos.InquiryDto;
import com.iwomi.nofiaPay.dtos.ReplyDto;
import com.iwomi.nofiaPay.frameworks.data.entities.EnrollEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.InquiryEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ReplyEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.replyRepository.IReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class InquiryRepository {

    private final IInquiryRepository repository;

    private  final IReplyRepository replyRepository;

    private final IInquiryMapper mapper;

    private  final IReplyMapper replyMapper;

    public List<InquiryEntity> getAllInquiries() {
        return repository.findAll();
    }

    public InquiryEntity createInquiry(InquiryDto dto) {
        InquiryEntity inquiry = mapper.mapToEntity(dto);
        log.info(" Inquiry to Save: {}", inquiry);
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

    public  ReplyEntity  replyInquiry(UUID uuid, ReplyDto dto){
        InquiryEntity inquiry = getOne(uuid);
        ReplyEntity reply = replyMapper.mapToEntity(dto) ;
        reply.setInquiry(inquiry);
        return replyRepository.save(reply);
    }

}
