package com.iwomi.nofiaPay.frameworks.data.repositories.clients;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.mappers.IClientMapper;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientRepository {
    private final IClientRepository repository;
    private final IClientMapper mapper;

    public List<ClientEntity> getAllClients() {
        return repository.findAll();
    }

    @Transactional
    public List<ClientEntity> createManyClients(List<ClientEntity> clients) {
        repository.deleteAll();
        return repository.saveAll(clients);
    }

    public ClientEntity getOne(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Client not found."));
    }

    public ClientEntity getOneByClientCode(String code) {
        return repository.findByClientCode(code)
                .orElseThrow(() -> new GeneralException("Client not found."));
    }

    public ClientEntity getOneByPhone(String phone) {
        return repository.findByPhoneNumber(phone)
                .orElseThrow(() -> new GeneralException("Client not found."));
    }

    public void deleteAccount(UUID uuid) {
        repository.deleteById(uuid);
    }

    public List<ClientEntity> getAllByClientCodes(List<String> codes) {
        return repository.findByClientCodeIn(codes);
    }

}
