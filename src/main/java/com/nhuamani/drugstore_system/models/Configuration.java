package com.nhuamani.drugstore_system.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "configurations")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String settingKey;

    @Column(columnDefinition = "TEXT")
    private String settingValue;

    @Column(length = 255)
    private String description;

    // getters y setters
}
