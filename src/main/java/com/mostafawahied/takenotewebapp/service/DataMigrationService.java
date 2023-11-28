package com.mostafawahied.takenotewebapp.service;

import org.springframework.security.core.Authentication;

public interface DataMigrationService {
    void migrateReadingLevels();
}
