package com.iwomi.nofiaPay.services.validations;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IvalidationService {
    ValidationEntity sendToSubscriptionValidation(String clientCode);
    ValidationEntity sendToTellerValidation(String tellerClientCode, UUID transactionId, String agentAccount, ValidationTypeEnum type);
    ValidationEntity validate(String clientCode, String userid, ValidationStatusEnum status);
    Transaction validateTransfer(String clientCode, String userid, ValidationStatusEnum status);
    List<ClientEntity> viewByStatus(UserTypeEnum role, ValidationStatusEnum status);
    Boolean canValidate(String profile);
    Map<String, Object> tellerValidationRequests(String tellerClientCode);
}
