package com.iwomi.nofiaPay.frameworks.data.repositories.clients;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.mappers.IClientMapper;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientRepository {
    private final IClientRepository repository;
    private final IClientMapper mapper;

    public List<ClientEntity> getAllBranches() {
        return repository.findAll();
    }

//    public BranchEntity createBranch(BranchDto dto) {
//        BranchEntity entity = mapper.mapToEntity(dto);
//        return repository.save(entity);
//    }

    public ClientEntity getOne(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Client not found."));
    }

//    public BranchEntity updateAccount(UUID uuid, BranchDto dto) {
//        BranchEntity entity = getOne(uuid);
//        mapper.updateBranchFromDto(dto, entity);
//        return repository.save(entity);
//    }

    public void deleteAccount(UUID uuid) {
        repository.deleteById(uuid);
    }

    public ClientEntity getOneByBranchAndClientCode(String branchId, String code) {
        return repository.findByBranchIdAndClientCode(branchId, code)
                .orElseThrow(() -> new GeneralException("Client not found."));
    }
}