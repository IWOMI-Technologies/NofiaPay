package com.iwomi.nofiaPay.services.clients;

import com.iwomi.nofiaPay.dtos.responses.Account;
import com.iwomi.nofiaPay.dtos.responses.Client;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    List<Client> findAllClient();
//    Branch saveClient(BranchDto dto);
    Client viewOne(UUID uuid);
//    Branch update(UUID uuid, BranchDto dto);
    void deleteOne(UUID uuid);

    Client viewOneByPhone(String phone);
    Client viewOneByClientCode(String clientCode);
    Client viewOneByAccountNumber(String accountNumber);

    List<Client> findAllByClientCode(UserTypeEnum role);

    List<Client> findAllDeletedByClientCode(String role);


}
