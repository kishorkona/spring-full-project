package com.work.repository;

import com.work.entities.ConfigurationEntity;
import com.work.entities.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Long> {
    List<ConfigurationEntity> findByType(String type);
}