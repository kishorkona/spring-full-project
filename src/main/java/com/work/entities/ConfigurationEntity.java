package com.work.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "configuration")
public class ConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "property_key")
    private String propertyKey;

    @Column(name = "property-value")
    private String propertyValue;

    @Column(name = "type")
    private String type;
}
