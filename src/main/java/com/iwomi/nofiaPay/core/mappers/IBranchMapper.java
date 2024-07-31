package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.domain.entities.Branch;
import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IBranchMapper {
    BranchDto mapToDto(BranchEntity entity);
    BranchEntity mapToEntity(BranchDto dto);
    Branch mapToModel(BranchEntity entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBranchFromDto(BranchDto dto, @MappingTarget BranchEntity entity);
}
