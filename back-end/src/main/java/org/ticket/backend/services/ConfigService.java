package org.ticket.backend.services;

import org.springframework.stereotype.Service;
import org.ticket.backend.models.Configuration;
import org.ticket.backend.repositories.ConfigRepository;

@Service
public class ConfigService {

    private ConfigRepository configRepository;

    public void addConfig(Configuration configuration) {
        configRepository.save(configuration);
    }

    public boolean editConfig(Long executeId, Configuration configuration) {
        return false;
    }

    public boolean deleteConfig(Long executeId) {
        return false;
    }
}
