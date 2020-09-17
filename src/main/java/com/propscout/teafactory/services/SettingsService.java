package com.propscout.teafactory.services;

import com.propscout.teafactory.models.entities.Settings;
import com.propscout.teafactory.repositories.SettingsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

    private SettingsRepository settingsRepository;

    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Optional<Settings> getSettingsById(Integer id) {
        return settingsRepository.findById(id);
    }

    public void updateSettings(Settings settings) {

        if (settingsRepository.existsById(1)) settings.setId(1);

        settingsRepository.save(settings);

    }
}
