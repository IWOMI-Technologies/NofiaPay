package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.dtos.TransactionDto;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ITransactionMapper {
    TransactionDto mapToDto(TransactionEntity entity);
    TransactionEntity mapToEntity(TransactionDto dto);
    Transaction mapToModel(TransactionEntity entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTransactionFromDto(TransactionDto dto, @MappingTarget TransactionEntity entity);
}
