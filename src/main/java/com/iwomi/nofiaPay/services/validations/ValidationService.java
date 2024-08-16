package com.iwomi.nofiaPay.services.validations;

import com.iwomi.nofiaPay.core.constants.AppConst;
import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.SubscriptionValidationEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidatorEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.subscribtionValidation.SubscriptionValidationRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.validators.ValidatorRepository;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationService implements IvalidationService {

    private final SubscriptionValidationRepository repository;
    private final ClientRepository clientRepository;
    private final ValidatorRepository validatorRepository;
    private final AuthClient authClient;

    @Override
    public SubscriptionValidationEntity sendToValidation(String clientCode) {
        return repository.createValidation(clientCode);
    }

    @Override
    public SubscriptionValidationEntity validate(String clientCode, String userid) {
        SubscriptionValidationEntity entity = repository.getByClientCode(clientCode);
        if (entity.getStatus() != ValidationStatusEnum.VALIDATED) {
            entity.setStatus(ValidationStatusEnum.VALIDATED);
            return repository.updateSubscription(entity);
        }

        return entity;
    }

    @Override
    public List<ClientEntity> viewByStatus(UserTypeEnum role, ValidationStatusEnum status) {
        // get cliend codes from auth ms with specific role
        List<String> clientCodes = (List<String>) authClient.getUsersByRole(role).getBody();
        // get appropriate client codes
        List<String> inValidationCodes = repository.getAllByClientCodes(clientCodes)
                .stream()
                .filter(entity -> entity.getStatus() == status) // filter or get those with wanted status
                .map(SubscriptionValidationEntity::getSubscriberClientCode)// get client codes
                .toList();

        return clientRepository.getAllByClientCodes(inValidationCodes);
    }

    @Override
    public Boolean canValidate(String profile) {
        ValidatorEntity validator = validatorRepository.getOneByProcess(AppConst.SUBCRIPTION);
        if (validator.getProfiles() == null) return false;

        return validator.getProfiles().contains(profile);
    }
}
