package com.nhuamani.drugstore_system.repositories;

import com.nhuamani.drugstore_system.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Buscar categorías principales (sin categoría padre)
    List<Category> findByParentIsNull();

    // Buscar subcategorías de una categoría
    List<Category> findByParentId(Long parentId);

    // Buscar categorías activas
    List<Category> findByEnabledTrue();

    // Buscar categorías por estado y categoría padre
    List<Category> findByParentIdAndEnabledTrue(Long parentId);

    // Buscar por nombre
    Optional<Category> findByNameIgnoreCase(String name);

    // Verificar si existe una categoría con ese nombre
    boolean existsByNameIgnoreCase(String name);

    // Verificar si existe una categoría con ese nombre dentro de una categoría padre
    boolean existsByNameIgnoreCaseAndParentId(String name, Long parentId);

    // Comprobar nombres duplicados en categorías principales
    boolean existsByNameIgnoreCaseAndParentIsNull(String name);

    // Comprobar duplicados al actualizar una categoría principal
    boolean existsByNameIgnoreCaseAndParentIsNullAndIdNot(String name, Long id);

    // Comprobar duplicados al actualizar subcategorías
    boolean existsByNameIgnoreCaseAndParentIdAndIdNot(String name, Long parentId, Long id);

    // Buscar subcategorías activas
    boolean existsByParentIdAndEnabledTrue(Long parentId);

    @Query("""
        SELECT c
        FROM Category c
        LEFT JOIN c.parent p
        WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(COALESCE(c.description, '')) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(COALESCE(p.name, '')) LIKE LOWER(CONCAT('%', :term, '%'))
        ORDER BY c.name ASC
    """)
    List<Category> searchByTerm(@Param("term") String term);
}
