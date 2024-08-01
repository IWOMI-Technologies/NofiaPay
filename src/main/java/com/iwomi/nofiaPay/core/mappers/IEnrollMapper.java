package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.dtos.responses.Enroll;
import com.iwomi.nofiaPay.frameworks.data.entities.EnrollEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IEnrollMapper {
    EnrollEntity mapToEntity(EnrollDto dto);
    Enroll mapToModel(EnrollEntity entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(EnrollDto dto, @MappingTarget EnrollEntity entity);
}
