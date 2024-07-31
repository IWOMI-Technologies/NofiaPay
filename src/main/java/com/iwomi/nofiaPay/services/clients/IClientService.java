package com.iwomi.nofiaPay.services.clients;

import com.iwomi.nofiaPay.domain.entities.Branch;
import com.iwomi.nofiaPay.domain.entities.Client;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    List<Client> findAllClient();
//    Branch saveClient(BranchDto dto);
    Client viewOne(UUID uuid);
//    Branch update(UUID uuid, BranchDto dto);
    void deleteOne(UUID uuid);
}
