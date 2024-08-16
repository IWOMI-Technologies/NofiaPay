package com.iwomi.nofiaPay.services.validations;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.dtos.responses.Branch;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.SubscriptionValidationEntity;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;

import java.util.List;

public interface IvalidationService {
    SubscriptionValidationEntity sendToValidation(String clientCode);
    SubscriptionValidationEntity validate(String clientCode, String userid);
    List<ClientEntity> viewByStatus(UserTypeEnum role, ValidationStatusEnum status);
    Boolean canValidate(String profile);
}
