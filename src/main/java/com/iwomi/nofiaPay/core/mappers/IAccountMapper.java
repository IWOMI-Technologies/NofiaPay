package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.dtos.AccountDto;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IAccountMapper {
    AccountDto mapToDto(AccountEntity entity);

    AccountEntity mapToEntity(AccountDto dto);

    Account mapToModel(AccountEntity entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccountFromDto(AccountDto dto, @MappingTarget AccountEntity entity);

}
