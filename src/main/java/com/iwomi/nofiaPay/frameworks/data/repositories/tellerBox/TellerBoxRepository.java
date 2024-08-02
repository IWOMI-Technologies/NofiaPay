package com.iwomi.nofiaPay.frameworks.data.repositories.tellerBox;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.frameworks.data.entities.BranchEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TellerBoxEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TellerBoxRepository {
    private final ITellerBoxRepository repository;

    public TellerBoxEntity getOneByNumber(String number, String branchId) {
        return repository.findByNumberAndBranchId(number, branchId)
                .orElseThrow(() -> new GeneralException("Branch not found."));
    }
}
