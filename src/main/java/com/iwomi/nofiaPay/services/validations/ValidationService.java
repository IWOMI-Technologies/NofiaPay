package com.iwomi.nofiaPay.services.validations;

import com.iwomi.nofiaPay.core.constants.AppConst;
import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidatorEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.Validation.ValidationRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.validators.ValidatorRepository;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserStatusEnum;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;
import com.iwomi.nofiaPay.services.transactions.TransactionService;
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
    private final TransactionService transactionService;

    @Override
    public ValidationEntity sendToSubscriptionValidation(String clientCode) {
//        System.out.println("IN SERVICE sendToSubscriptionValidation");
//        ValidationEntity found = repository.getByClientCode(clientCode);
//        System.out.println("IN SERVICE sendToSubscriptionValidation 22222 "+found);
//
//        if (found != null) {
//            System.out.println("Client already exists and awaiting validation");
//            return found;
//        }
//        System.out.println("AFTERRRRRR sendToSubscriptionValidation");

        ValidationEntity validation = ValidationEntity.builder()
                .subscriberClientCode(clientCode)
                .status(ValidationStatusEnum.PENDING)
                .type(ValidationTypeEnum.SUBSCRIPTION)
                .build();
        System.out.println("BEFORE RETURNING VALIDATION");

        return repository.createValidation(validation);
    }

    @Override
    public ValidationEntity sendToTellerValidation(String tellerClientCode, UUID transactionId, String agentAccount, ValidationTypeEnum type) {
        List<TransactionEntity> transactions = transactionRepository
                .getByIssuerAndCreatedAtBtw(agentAccount)
                .stream()
                .filter(trans -> trans.getType() != OperationTypeEnum.REVERSEMENT) // remove all reversement transactions
                .filter(entity -> entity.getStatus() == StatusTypeEnum.COLLECTED || entity.getStatus() == StatusTypeEnum.VALIDATED)
                .toList();
        BigDecimal total = transactions.stream()
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("transactions done by agent today" + transactions.stream().map(TransactionEntity::getAmount).toList());
        System.out.println("Total amount done by agent today" + total.toPlainString());

        ValidationEntity validation = ValidationEntity.builder()
                .tellerClientCode(tellerClientCode)
                .type(type)
                .expectedAmount(total)
                .transactionId(transactionId)
                .status(ValidationStatusEnum.PENDING)
                .build();

        System.out.println("BEFORE RETURNING VALIDATION  " + validation.getExpectedAmount());
        ValidationEntity saved = repository.createValidation(validation);
        return saved;
    }

    @Override
    public ValidationEntity validate(String clientCode, String userid, ValidationStatusEnum status) {
        ValidationEntity entity = repository.getByClientCode(clientCode);
        if (entity.getStatus() == ValidationStatusEnum.PENDING) {
            entity.setStatus(status);
            entity.setValidatedBy(userid);

            // Activate user when validated
            if (entity.getType() == ValidationTypeEnum.SUBSCRIPTION)
                authClient.changeStatus(clientCode, UserStatusEnum.ACTIVE);


            return repository.updateSubscription(entity);
        }
//        if (status == ValidationStatusEnum.VALIDATED)
//            websocketService.sendToUser(userid, StatusTypeEnum.VALIDATED.toString());
//        else websocketService.sendToUser(userid, StatusTypeEnum.VALIDATED.toString());

        return entity;
    }

    @Override
    public ValidationEntity validateTransfer(String clientCode, String userid, ValidationStatusEnum status) {
        ValidationEntity entity = repository.getByClientCode(clientCode);
        if (entity.getStatus() == ValidationStatusEnum.PENDING) {
            entity.setStatus(status);
            entity.setValidatedBy(userid);

            // Update transaction status
            transactionRepository.updateTransactionStatus(entity.getTransactionId(), StatusTypeEnum.COLLECTED);
            return repository.updateSubscription(entity);
        }

        return entity;
    }

    @Override
    public List<ClientEntity> viewByStatus(UserTypeEnum role, ValidationStatusEnum status) {
        // get client codes from auth ms with specific role
        List<String> clientCodes = (List<String>) authClient.getUsersByRole(role).getBody();
        System.out.println("clientCodes: " + clientCodes);
        // get appropriate client codes
        List<String> inValidationCodes = repository.getAllByClientCodes(clientCodes)
                .stream()
                .filter(entity -> entity.getStatus() == status && entity.getType() == ValidationTypeEnum.SUBSCRIPTION) // filter or get those with wanted status
                .map(ValidationEntity::getSubscriberClientCode)// get client codes
                .toList();
        System.out.println("inValidationCodes: " + inValidationCodes);
        return clientRepository.getAllByClientCodes(inValidationCodes);
    }

    @Override
    public Boolean canValidate(String profile) {
        System.out.println("IN CAN VALIDATE SERVICE");
        ValidatorEntity validator = validatorRepository.getOneByProcess(AppConst.SUBCRIPTION);
        System.out.println("validator: " + validator);
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

        List<TransactionEntity> transactions = transactionRepository.getAllByTransactionIds(ids)
                .stream()
                .filter(entity -> entity.getStatus() == StatusTypeEnum.COLLECTED || entity.getStatus() == StatusTypeEnum.VALIDATED)
                .toList();

        // put similar id in a map
        List<Map<String, Object>> result = new ArrayList<>();

        return Map.of(
                "validations", validations,
                "transactions", transactions
        );
    }
}
