package com.iwomi.nofiaPay.core.mappers;

import com.iwomi.nofiaPay.dtos.ReplyDto;
import com.iwomi.nofiaPay.dtos.responses.Reply;
import com.iwomi.nofiaPay.frameworks.data.entities.ReplyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IReplyMapper {
    ReplyEntity mapToEntity (ReplyDto dto);

    Reply mapToModel(ReplyEntity entity);
}
