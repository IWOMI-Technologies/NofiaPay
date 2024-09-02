package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.dtos.InquiryDto;
import com.iwomi.nofiaPay.dtos.responses.Inquiry;
import com.iwomi.nofiaPay.frameworks.data.entities.InquiryEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IInquiryMapper {

    InquiryEntity mapToEntity(InquiryDto dto);

    Inquiry mapToModel(InquiryEntity entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(InquiryDto dto, @MappingTarget InquiryEntity entity);
}
