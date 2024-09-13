package com.iwomi.nofiaPay.services.inquiries;

import com.iwomi.nofiaPay.dtos.InquiryDto;
import com.iwomi.nofiaPay.dtos.ReplyDto;
import com.iwomi.nofiaPay.dtos.responses.Inquiry;
import com.iwomi.nofiaPay.dtos.responses.Reply;

import java.util.List;
import java.util.UUID;

public interface IInquiryService {
    List<Inquiry> viewAllInquiries();
    Inquiry saveInquiry(InquiryDto dto);

    Inquiry viewOne(UUID uuid);

    Reply replyInquiry(UUID uuid, ReplyDto dto );

    Inquiry updateInquiry(UUID uuid, InquiryDto dto);

    void deleteOne(UUID uuid);
}
