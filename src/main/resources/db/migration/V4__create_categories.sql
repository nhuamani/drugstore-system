CONSTRAINT fk_categories_parent
    FOREIGN KEY (parent_id)
    REFERENCES categories(id)
    ON DELETE RESTRICT