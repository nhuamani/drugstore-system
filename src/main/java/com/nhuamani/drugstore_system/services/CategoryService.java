package com.nhuamani.drugstore_system.services;

import com.nhuamani.drugstore_system.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    List<Category> findRootCategories();

    List<Category> findChildren(Long parentId);

    List<Category> findAvailableParents(Long categoryId);

    Category findById(Long id);

    Category create(String name,
                    String description,
                    Long parentId,
                    boolean enabled
    );

    Category update(
            Long id,
            String name,
            String description,
            Long parentId,
            boolean enabled
    );

    void disable(Long id);

    List<Category> search(String term);

}
