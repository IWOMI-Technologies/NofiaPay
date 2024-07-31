package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.domain.entities.Client;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IClientMapper {
//    ClientDto mapToDto(ClientEntity entity);
//    ClientEntity mapToEntity(ClientDto dto);
    Client mapToModel(ClientEntity entity);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateClientFromDto(ClientDto dto, @MappingTarget ClientEntity entity);
}
