package com.iwomi.nofiaPay.services.enquiries;

import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.InquiryDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.Inquiry;

import java.util.List;
import java.util.UUID;

public interface IInquiryService {
    List<Inquiry> viewAllInquiries();
    Inquiry saveInquiry(InquiryDto dto);

    Inquiry viewOne(UUID uuid);

    Inquiry updateInquiry(UUID uuid, InquiryDto dto);
    void deleteOne(UUID uuid);
}
