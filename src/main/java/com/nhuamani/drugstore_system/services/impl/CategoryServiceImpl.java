package com.nhuamani.drugstore_system.services.impl;

import com.nhuamani.drugstore_system.models.Category;
import com.nhuamani.drugstore_system.repositories.CategoryRepository;
import com.nhuamani.drugstore_system.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findRootCategories() {
        return categoryRepository.findByParentIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findChildren(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return getCategory(id);
    }

    @Override
    public Category create(
            String name,
            String description,
            Long parentId,
            boolean enabled
    ) {

        String normalizedName = normalizeName(name);

        validateDuplicateName(normalizedName, parentId, null);

        Category category = new Category();
        category.setName(normalizedName);
        category.setDescription(normalizeDescription(description));
        category.setEnabled(enabled);

        if (parentId != null) {
            category.setParent(getCategory(parentId));
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category update(
            Long id,
            String name,
            String description,
            Long parentId,
            boolean enabled
    ) {
        Category category = getCategory(id);

        String normalizedName = normalizeName(name);

        validateDuplicateName(normalizedName, parentId, id);

        Category newParent = null;

        if (parentId != null) {
            newParent = getCategory(parentId);
            validateParent(category, newParent);
        }

        category.setName(normalizedName);
        category.setDescription(normalizeDescription(description));
        category.setParent(newParent);
        category.setEnabled(enabled);

        return categoryRepository.save(category);
    }

    @Override
    public void disable(Long id) {

        Category category = getCategory(id);

        if (categoryRepository.existsByParentIdAndEnabledTrue(id)) {
            throw new IllegalStateException(
                    "No se puede desactivar una categoría que tiene subcategorías activas."
            );
        }

        category.setEnabled(false);

        categoryRepository.save(category);
    }

    // -------------------------
    // Métodos privados
    // -------------------------

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró la categoría con ID: " + id
                ));
    }

    private String normalizeName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la categoría es obligatorio."
            );
        }

        return name.trim();
    }

    private String normalizeDescription(String description) {
        return description == null || description.isBlank()
                ? null
                : description.trim();
    }

    private void validateDuplicateName(
            String name,
            Long parentId,
            Long currentId
    ) {
        boolean exists;

        if (parentId == null) {

            exists = currentId == null
                    ? categoryRepository.existsByNameIgnoreCaseAndParentIsNull(name)
                    : categoryRepository.existsByNameIgnoreCaseAndParentIsNullAndIdNot(
                    name, currentId
            );

        } else {

            exists = currentId == null
                    ? categoryRepository.existsByNameIgnoreCaseAndParentId(name, parentId)
                    : categoryRepository.existsByNameIgnoreCaseAndParentIdAndIdNot(
                    name, parentId, currentId
            );
        }

        if (exists) {
            throw new IllegalArgumentException(
                    "Ya existe una categoría con ese nombre en el mismo nivel."
            );
        }
    }

    private void validateParent(Category category, Category newParent) {

        if (category.getId().equals(newParent.getId())) {
            throw new IllegalArgumentException(
                    "Una categoría no puede ser su propia categoría padre."
            );
        }

        // Recorrer los padres para evitar ciclos jerárquicos.
        Set<Long> visited = new HashSet<>();

        Category current = newParent;

        while (current != null) {

            if (!visited.add(current.getId())) {
                throw new IllegalStateException(
                        "Se detectó una jerarquía de categorías inválida."
                );
            }

            if (current.getId().equals(category.getId())) {
                throw new IllegalArgumentException(
                        "No puedes asignar como padre una subcategoría propia."
                );
            }

            current = current.getParent();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAvailableParents(Long categoryId) {

        Category category = getCategory(categoryId);

        Set<Long> excludedIds = new HashSet<>();

        collectDescendants(category, excludedIds);

        return categoryRepository.findByEnabledTrue()
                .stream()
                .filter(parent -> !excludedIds.contains(parent.getId()))
                .toList();
    }

    private void collectDescendants(
            Category category,
            Set<Long> excludedIds
    ) {
        if (!excludedIds.add(category.getId())) {
            return;
        }

        categoryRepository.findByParentId(category.getId())
                .forEach(child -> collectDescendants(child, excludedIds));
    }
}