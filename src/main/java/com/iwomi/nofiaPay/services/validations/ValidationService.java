package com.iwomi.nofiaPay.services.validations;

import com.iwomi.nofiaPay.core.constants.AppConst;
import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidatorEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.Validation.ValidationRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.validators.ValidatorRepository;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidationService implements IvalidationService {

    private final ValidationRepository repository;
    private final ClientRepository clientRepository;
    private final ValidatorRepository validatorRepository;
    private final ValidationRepository validation;
    private final TransactionRepository transactionRepository;
    private final AuthClient authClient;

    @Override
    public ValidationEntity sendToSubscriptionValidation(String clientCode) {
        ValidationEntity validation = ValidationEntity.builder()
                .subscriberClientCode(clientCode)
                .status(ValidationStatusEnum.PENDING)
                .type(ValidationTypeEnum.SUBSCRIPTION)
                .build();
        return repository.createValidation(validation);
    }

    @Override
    public ValidationEntity sendToTellerValidation(String tellerClientCode, UUID transactionId, String agentAccount, ValidationTypeEnum type) {
        List<TransactionEntity> transactions = transactionRepository.getByIssuerAndCreatedAtBtw(agentAccount);
        BigDecimal total = transactions.stream()
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ValidationEntity validation = ValidationEntity.builder()
                .tellerClientCode(tellerClientCode)
                .type(type)
                .expectedAmount(total)
                .transactionId(transactionId)
                .status(ValidationStatusEnum.PENDING)
                .build();

        return repository.createValidation(validation);
    }

    @Override
    public ValidationEntity validate(String clientCode, String userid) {
        ValidationEntity entity = repository.getByClientCode(clientCode);
        if (entity.getStatus() != ValidationStatusEnum.VALIDATED) {
            entity.setStatus(ValidationStatusEnum.VALIDATED);
            entity.setValidatedBy(userid);
            return repository.updateSubscription(entity);
        }

        return entity;
    }

    @Override
    public List<ClientEntity> viewByStatus(UserTypeEnum role, ValidationStatusEnum status) {
        // get client codes from auth ms with specific role
        List<String> clientCodes = (List<String>) authClient.getUsersByRole(role).getBody();
        // get appropriate client codes
        List<String> inValidationCodes = repository.getAllByClientCodes(clientCodes)
                .stream()
                .filter(entity -> entity.getStatus() == status && entity.getType() == ValidationTypeEnum.SUBSCRIPTION) // filter or get those with wanted status
                .map(ValidationEntity::getSubscriberClientCode)// get client codes
                .toList();
        return clientRepository.getAllByClientCodes(inValidationCodes);
    }

    @Override
    public Boolean canValidate(String profile) {
        ValidatorEntity validator = validatorRepository.getOneByProcess(AppConst.SUBCRIPTION);
        if (validator.getProfiles() == null) return false;

        return validator.getProfiles().contains(profile.toLowerCase());
    }

    @Override
    public Map<String, Object> tellerValidationRequests(String tellerClientCode) {
        List<ValidationEntity> validations = validation.getAllTellerAndType(tellerClientCode, ValidationTypeEnum.REVERSEMENT)
                .stream()
                .filter(entity -> entity.getStatus() == ValidationStatusEnum.PENDING)   // get only pendings
                .toList();

        List<UUID> ids = validations.stream().map(ValidationEntity::getTransactionId).toList();

        List<TransactionEntity> transactions = transactionRepository.getAllByTransactionIds(ids);

        // put similar id in a map
        List<Map<String, Object>> result =   new ArrayList<>();

        return Map.of(
                "validations", validations,
                "transactions", transactions
        );
    }
}
