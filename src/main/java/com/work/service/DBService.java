package com.work.service;

import com.work.entities.ConfigurationEntity;
import com.work.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DBService {

    @Autowired
    ConfigurationRepository configurationRepository;

    public Map<String, String> getAWSProperties() {
        Map<String, String> configValues = new HashMap<>();
        List<ConfigurationEntity> configurationEntityList = configurationRepository.findByType("AWS");
        configurationEntityList.forEach(config -> {
            if(config.getPropertyValue() == null || config.getPropertyValue().trim().isEmpty()) {
                configValues.put(config.getPropertyKey().trim(), null);
            }
            configValues.put(config.getPropertyKey().trim(), config.getPropertyValue().trim());
        });
        return configValues;
    }
}