package com.iwomi.nofiaPay.services.inquiries;

import com.iwomi.nofiaPay.core.mappers.IInquiryMapper;
import com.iwomi.nofiaPay.core.mappers.IReplyMapper;
import com.iwomi.nofiaPay.dtos.InquiryDto;
import com.iwomi.nofiaPay.dtos.ReplyDto;
import com.iwomi.nofiaPay.dtos.responses.Inquiry;
import com.iwomi.nofiaPay.dtos.responses.Reply;
import com.iwomi.nofiaPay.frameworks.data.repositories.inquiryRepository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InquiryService implements IInquiryService {
    private final InquiryRepository inquiryRepository;
    private final IInquiryMapper mapper;
    private  final IReplyMapper replyMapper;

    @Override
    public List<Inquiry> viewAllInquiries() {
        return inquiryRepository.getAllInquiries()
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public Inquiry saveInquiry(InquiryDto dto) {
        return mapper.mapToModel(inquiryRepository.createInquiry(dto));
    }

    @Override
    public Inquiry viewOne(UUID uuid) {
        return mapper.mapToModel(inquiryRepository.getOne(uuid));
    }

    @Override
    public Reply replyInquiry(UUID uuid, ReplyDto dto) {
        return replyMapper.mapToModel(inquiryRepository.replyInquiry(uuid, dto));
    }


    @Override
    public Inquiry updateInquiry(UUID uuid, InquiryDto dto) {
        return mapper.mapToModel(inquiryRepository.updateInquiry(uuid, dto));
    }

    @Override
    public void deleteOne(UUID uuid) {
        inquiryRepository.deleteInquiry(uuid);
    }
}
