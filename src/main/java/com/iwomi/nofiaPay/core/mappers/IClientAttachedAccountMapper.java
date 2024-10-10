package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.dtos.ClientAttachedAccountDto;
import com.iwomi.nofiaPay.dtos.responses.ClientAttachedAccounts;
import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientAttachedAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IClientAttachedAccountMapper {
    ClientAttachedAccountEntity mapToEntity(ClientAttachedAccountDto dto);
    ClientAttachedAccounts mapToModel (ClientAttachedAccountEntity entity);
}
