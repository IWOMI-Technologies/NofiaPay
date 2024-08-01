package com.iwomi.nofiaPay.services.branches;

import com.iwomi.nofiaPay.dtos.responses.Branch;
import com.iwomi.nofiaPay.dtos.BranchDto;

import java.util.List;
import java.util.UUID;

public interface IBranchService {
    List<Branch> findAllBranches();
    Branch saveBranch(BranchDto dto);
    Branch viewOne(UUID uuid);
    Branch update(UUID uuid, BranchDto dto);
    void deleteOne(UUID uuid);
}
