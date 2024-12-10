package org.ticket.backend.services;

import org.springframework.stereotype.Service;
import org.ticket.backend.models.Configuration;
import org.ticket.backend.repositories.ConfigRepository;

import java.util.Optional;

@Service
public class ConfigService {

    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public void addConfig(Configuration configuration) {
        configRepository.save(configuration);
    }

    public Configuration getConfig() {
        Optional<Configuration> config = configRepository.findById(1);
        return config.orElseThrow(() -> new RuntimeException("Configuration not found"));
    }
}
