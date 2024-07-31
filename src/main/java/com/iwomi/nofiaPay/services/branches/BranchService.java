package com.iwomi.nofiaPay.services.branches;

import com.iwomi.nofiaPay.core.mappers.IBranchMapper;
import com.iwomi.nofiaPay.domain.entities.Branch;
import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService{

    private final BranchRepository branchRepository;
    private final IBranchMapper mapper;

    @Override
    public List<Branch> findAllBranches() {
        return branchRepository.getAllBranches()
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public Branch saveBranch(BranchDto dto) {
        return mapper.mapToModel(branchRepository.createBranch(dto));
    }

    @Override
    public Branch viewOne(UUID uuid) {
        return mapper.mapToModel(branchRepository.getOne(uuid));
    }

    @Override
    public Branch update(UUID uuid, BranchDto dto) {
        return mapper.mapToModel(branchRepository.updateAccount(uuid, dto));
    }

    @Override
    public void deleteOne(UUID uuid) {
        branchRepository.deleteAccount(uuid);
    }
}
