package com.iwomi.nofiaPay.frameworks.data.repositories.branches;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BranchRepository {
    private final IBranchRepository repository;


}
