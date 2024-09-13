package com.iwomi.nofiaPay.services.tellerBox;

import com.iwomi.nofiaPay.frameworks.data.repositories.tellerBox.TellerBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TellerBoxService implements ITellerBoxService {
    private final TellerBoxRepository tellerBoxRepository;


}
