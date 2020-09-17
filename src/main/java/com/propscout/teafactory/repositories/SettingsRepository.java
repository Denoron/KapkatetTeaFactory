package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.entities.Settings;
import org.springframework.data.repository.CrudRepository;

public interface SettingsRepository extends CrudRepository<Settings, Integer> {
}
