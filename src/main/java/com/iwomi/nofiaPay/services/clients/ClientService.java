package com.iwomi.nofiaPay.services.clients;

import com.iwomi.nofiaPay.core.mappers.IBranchMapper;
import com.iwomi.nofiaPay.core.mappers.IClientMapper;
import com.iwomi.nofiaPay.dtos.responses.Branch;
import com.iwomi.nofiaPay.dtos.responses.Client;
import com.iwomi.nofiaPay.frameworks.data.repositories.branches.BranchRepository;
import com.iwomi.nofiaPay.frameworks.data.repositories.clients.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;
    private final BranchRepository branchRepository;
    private final IClientMapper mapper;
    private final IBranchMapper branchMapper;

    @Override
    public List<Client> findAllClient() {
        return clientRepository.getAllBranches()
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
    public Client viewOneByBranchAndClientCode(String branchId, String code) {
        UUID uuid = UUID.fromString(branchId);
        Branch branch = branchMapper.mapToModel(branchRepository.getOne(uuid));
        Client client = mapper.mapToModel(clientRepository.getOneByBranchAndClientCode(branchId, code));
        client.setBranch(branch);

        return client;
    }
}
