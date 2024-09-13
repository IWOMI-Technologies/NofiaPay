package com.iwomi.nofiaPay.frameworks.data.repositories.branches;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.mappers.IBranchMapper;
import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BranchRepository {
    private final IBranchRepository repository;
    private final IBranchMapper mapper;

    public List<BranchEntity> getAllBranches() {
        return repository.findAll();
    }

    public BranchEntity createBranch(BranchDto dto) {
        BranchEntity entity = mapper.mapToEntity(dto);
        return repository.save(entity);
    }

    public BranchEntity getOne(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new GeneralException("Branch not found."));
    }

    public BranchEntity getOneByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new GeneralException("Branch not found."));
    }

    public BranchEntity updateAccount(UUID uuid, BranchDto dto) {
        BranchEntity entity = getOne(uuid);
        mapper.updateBranchFromDto(dto, entity);
        return repository.save(entity);
    }

    public void deleteAccount(UUID uuid) {
        repository.deleteById(uuid);
    }
}
