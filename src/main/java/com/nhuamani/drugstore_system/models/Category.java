package com.nhuamani.drugstore_system.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "categories",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_categories_name_parent",
                    columnNames = {"name", "parent_id"}
            )
        },
        indexes = {
            @Index(
                    name = "idx_categories_parent",
                    columnList = "parent_id"
            ),
            @Index(
                    name = "idx_categories_enabled",
                    columnList = "enabled"
            )
        }
    )
@Getter
@Setter
@NoArgsConstructor
public class Category extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    // Categoría padre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // Subcategorías
    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @Column(nullable = false)
    private boolean enabled = true;
}