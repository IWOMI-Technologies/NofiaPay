package com.iwomi.nofiaPay.frameworks.data.repositories.clients;

import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IClientRepository extends JpaRepository<ClientEntity, UUID> {
    Optional<ClientEntity> findByPhoneNumber(String phone);
    Optional<ClientEntity> findByClientCode(String clientCode);
}
