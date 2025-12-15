package com.work.repository;

import com.work.entities.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotesRepository extends JpaRepository<NotesEntity, UUID> {

    List<NotesEntity> findByTitle(String title);

    List<NotesEntity> findByTitleAndNotes(String title, String notes);
}