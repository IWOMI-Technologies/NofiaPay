package com.iwomi.nofiaPay.services.clients;

import com.iwomi.nofiaPay.core.constants.NomenclatureConstants;
import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.enums.ValidationTypeEnum;
import com.iwomi.nofiaPay.core.mappers.IAccountMapper;
import com.iwomi.nofiaPay.core.mappers.IClientMapper;
import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.Client;
import com.iwomi.nofiaPay.frameworks.data.entities.AccountEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.Validation.ValidationRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.accounts.AccountRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import com.iwomi.nofiaPay.frameworks.externals.clients.AuthClient;
import com.iwomi.nofiaPay.frameworks.externals.dto.NomenclatureDto;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;
import com.iwomi.nofiaPay.frameworks.externals.nomenclature.NomenclatureClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ValidationRepository validationRepository;
    private final IClientMapper mapper;
    private final IAccountMapper accountMapper;
    private final AuthClient authClient;
    private final NomenclatureClient nomenclatureClient;

    @Override
    public List<Client> findAllClient() {
        return clientRepository.getAllClients()
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

//    @Override
//    public Branch saveBranch(BranchDto dto) {
//        return mapper.mapToModel(clientRepository.createBranch(dto));
//    }

    @Override
    public Client viewOne(UUID uuid) {
        return mapper.mapToModel(clientRepository.getOne(uuid));
    }

//    @Override
//    public Branch update(UUID uuid, BranchDto dto) {
//        return mapper.mapToModel(clientRepository.updateAccount(uuid, dto));
//    }

    @Override
    public void deleteOne(UUID uuid) {
        clientRepository.deleteAccount(uuid);
    }

    @Override
    public Client viewOneByPhone(String phone) {

        Client client = mapper.mapToModel(clientRepository.getOneByPhone(phone));
        List<String> accounts = accountRepository
                .getByClientCode(client.getClientCode())
                .stream()
                .map(AccountEntity::getAccountNumber)
                .toList();
        client.setAccounts(accounts);

        return client;
    }

    @Override
    public Client viewOneByClientCode(String clientCode) {

        Client client = mapper.mapToModel(clientRepository.getOneByClientCode(clientCode));


        Map<String, Object> nomenclatureResponse = nomenclatureClient.getSanmByTabcd(NomenclatureConstants.TellerAccountTypeCode);


        List<NomenclatureDto> data = (List<NomenclatureDto>) nomenclatureResponse.get("data");

       

        List<Account> accounts = accountRepository
                .getByClientCode(client.getClientCode())
                .stream()
                .map(accountMapper::mapToModel)
                .toList();
        List<String> accountNumbers = accounts
                .stream()
                .map(Account::getAccountNumber)
                .toList();

        client.setAccounts(accountNumbers);
//        client.setClientAccounts(accounts);

        return client;
    }

    @Override
    public Client viewOneByAccountNumber(String accountNumber) {
        AccountEntity account = accountRepository.getOneByAccount(accountNumber);
        return mapper.mapToModel(clientRepository.getOneByClientCode(account.getClientCode()));
    }

//    @Override
//    public Client viewAccountsByCode(String clientCode, String accountCode) {
//        return null;
//    }

    @Override
    public List<Client> findAllByClientCode(UserTypeEnum role) {
        System.out.println("before call ___________________");
        ResponseEntity<?> response = authClient.getUsersByRole(role);
        System.out.println("AFTER call ___________________");
        List<String> codes = (List<String>) response.getBody();

        // Get only validated clients
        List<String> validatedCodes = validationRepository.getAllByClientCodes(codes)
                .stream()
                .filter(entity -> entity.getStatus() == ValidationStatusEnum.VALIDATED) // filter or get those with wanted status
                .map(ValidationEntity::getSubscriberClientCode)// get client codes
                .toList();

        return clientRepository.getAllByClientCodes(validatedCodes)
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

    @Override
    public List<Client> findAllDeletedByClientCode(String role) {
        ResponseEntity<?> response = authClient.getUsersByRoleAndDeleted(role);
        List<String> codes = (List<String>) response.getBody();

        return clientRepository.getAllByClientCodes(codes)
                .stream()
                .map(mapper::mapToModel)
                .toList();
    }

}
