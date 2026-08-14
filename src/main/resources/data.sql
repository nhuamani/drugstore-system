INSERT IGNORE INTO `configurations` (`setting_key`, `setting_value`, `description`)
VALUES
    (
        'nombre_botica',
        'Botica el Angelito',
        'Nombre comercial de la farmacia/botica'
    ),
    ('ruc', '20123456789', 'RUC de la empresa'),
    (
        'direccion',
        'Av. Principal 123',
        'Dirección del establecimiento'
    ),
    ('telefono', '979 352 767', 'Teléfono principal'),
    ('moneda', 'S/', 'Símbolo de moneda'),
    ('igv', '18', 'Porcentaje de IGV');