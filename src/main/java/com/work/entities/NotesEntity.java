package com.work.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Notes")
@EntityListeners(AuditingEntityListener.class)
@Data
public class NotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "notes")
    private String notes;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;
}
