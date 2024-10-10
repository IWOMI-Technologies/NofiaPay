package com.iwomi.nofiaPay.frameworks.data.repositories.AttachedAccounts;

import com.iwomi.nofiaPay.frameworks.data.entities.ClientAttachedAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IClientAttachedAccountRepository extends JpaRepository<ClientAttachedAccountEntity, UUID>{
    List<ClientAttachedAccountEntity> findAccountsByClientCode(String clientCode);
}
