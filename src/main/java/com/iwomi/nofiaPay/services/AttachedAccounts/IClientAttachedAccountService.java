package com.iwomi.nofiaPay.services.AttachedAccounts;

import com.iwomi.nofiaPay.dtos.ClientAttachedAccountDto;
import com.iwomi.nofiaPay.dtos.responses.ClientAttachedAccounts;

import java.util.List;

public interface IClientAttachedAccountService {

    ClientAttachedAccounts saveAccount(ClientAttachedAccountDto dto);
    List<ClientAttachedAccounts> getAccountsByClientCode(String clientCode);

}
