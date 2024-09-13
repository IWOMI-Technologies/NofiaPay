package com.iwomi.nofiaPay.frameworks.data.repositories.replyRepository;

import com.iwomi.nofiaPay.frameworks.data.entities.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IReplyRepository extends JpaRepository<ReplyEntity, UUID> {
}
