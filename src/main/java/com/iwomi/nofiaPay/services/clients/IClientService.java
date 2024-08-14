package com.iwomi.nofiaPay.services.clients;

import com.iwomi.nofiaPay.dtos.responses.Client;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    List<Client> findAllClient();
//    Branch saveClient(BranchDto dto);
    Client viewOne(UUID uuid);
//    Branch update(UUID uuid, BranchDto dto);
    void deleteOne(UUID uuid);

    Client viewOneByPhone(String phone);

    List<Client> findAllByClientCode(String role);

    List<Client> findAllDeletedByClientCode(String role);
}
