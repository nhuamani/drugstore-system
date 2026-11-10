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
    ('igv', '18', 'Porcentaje de IGV'),
    ('logo', '', 'Nombre del archivo del logo de la empresa');


INSERT INTO categories (created_at, updated_at, description, enabled, name, parent_id)
VALUES
    (NOW(6), NOW(6), 'Medicamentos para diferentes tratamientos', b'1', 'Medicamentos', NULL),
    (NOW(6), NOW(6), 'Medicamentos para aliviar el dolor y la fiebre', b'1', 'Analgésicos y Antipiréticos', NULL),
    (NOW(6), NOW(6), 'Medicamentos para combatir infecciones', b'1', 'Antibióticos', NULL),
    (NOW(6), NOW(6), 'Productos para el sistema digestivo', b'1', 'Salud Digestiva', NULL),
    (NOW(6), NOW(6), 'Productos para enfermedades respiratorias', b'1', 'Salud Respiratoria', NULL),
    (NOW(6), NOW(6), 'Productos para el cuidado de la piel', b'1', 'Dermatología', NULL),
    (NOW(6), NOW(6), 'Vitaminas y suplementos nutricionales', b'1', 'Vitaminas y Suplementos', NULL),
    (NOW(6), NOW(6), 'Productos para primeros auxilios', b'1', 'Primeros Auxilios', NULL),
    (NOW(6), NOW(6), 'Productos para higiene personal', b'1', 'Higiene Personal', NULL),
    (NOW(6), NOW(6), 'Productos para bebés y recién nacidos', b'1', 'Bebés', NULL),
    (NOW(6), NOW(6), 'Productos para salud femenina', b'1', 'Salud Femenina', NULL),
    (NOW(6), NOW(6), 'Productos para salud masculina', b'1', 'Salud Masculina', NULL),
    (NOW(6), NOW(6), 'Dispositivos e instrumentos médicos', b'1', 'Equipos Médicos', NULL),
    (NOW(6), NOW(6), 'Productos para el cuidado bucal', b'1', 'Salud Bucal', NULL),
    (NOW(6), NOW(6), 'Productos para el cuidado de los ojos', b'1', 'Salud Ocular', NULL),
    (NOW(6), NOW(6), 'Productos para músculos y articulaciones', b'1', 'Salud Muscular', NULL),
    (NOW(6), NOW(6), 'Productos para enfermedades alérgicas', b'1', 'Antialérgicos', NULL),
    (NOW(6), NOW(6), 'Productos naturales y fitoterapéuticos', b'1', 'Productos Naturales', NULL),
    (NOW(6), NOW(6), 'Productos para el cuidado de adultos mayores', b'1', 'Geriatría', NULL),
    (NOW(6), NOW(6), 'Productos de protección y prevención sanitaria', b'1', 'Bioseguridad', NULL);


INSERT INTO categories (created_at, updated_at, description, enabled, name, parent_id)
VALUES
    -- Medicamentos (ID 3)
    (NOW(6), NOW(6), 'Medicamentos en tabletas y cápsulas', b'1', 'Tabletas y Cápsulas', 3),
    (NOW(6), NOW(6), 'Medicamentos en jarabes', b'1', 'Jarabes', 3),
    (NOW(6), NOW(6), 'Medicamentos en gotas', b'1', 'Gotas', 3),
    (NOW(6), NOW(6), 'Medicamentos inyectables', b'1', 'Inyectables', 3),

    -- Analgésicos y Antipiréticos (ID 4)
    (NOW(6), NOW(6), 'Medicamentos para aliviar dolores', b'1', 'Analgésicos', 4),
    (NOW(6), NOW(6), 'Medicamentos para reducir la fiebre', b'1', 'Antipiréticos', 4),
    (NOW(6), NOW(6), 'Medicamentos antiinflamatorios', b'1', 'Antiinflamatorios', 4),
    (NOW(6), NOW(6), 'Medicamentos para dolores musculares', b'1', 'Dolor Muscular', 4),

    -- Antibióticos (ID 5)
    (NOW(6), NOW(6), 'Antibióticos de uso oral', b'1', 'Antibióticos Orales', 5),
    (NOW(6), NOW(6), 'Antibióticos de uso tópico', b'1', 'Antibióticos Tópicos', 5),
    (NOW(6), NOW(6), 'Antibióticos en suspensión', b'1', 'Suspensiones Antibióticas', 5),
    (NOW(6), NOW(6), 'Antibióticos de uso oftálmico', b'1', 'Antibióticos Oftálmicos', 5),

    -- Salud Digestiva (ID 6)
    (NOW(6), NOW(6), 'Productos para la acidez estomacal', b'1', 'Antiácidos', 6),
    (NOW(6), NOW(6), 'Productos para la diarrea', b'1', 'Antidiarreicos', 6),
    (NOW(6), NOW(6), 'Productos para el estreñimiento', b'1', 'Laxantes', 6),
    (NOW(6), NOW(6), 'Productos para gases intestinales', b'1', 'Antiflatulentos', 6),

    -- Salud Respiratoria (ID 7)
    (NOW(6), NOW(6), 'Productos para aliviar la tos', b'1', 'Antitusivos', 7),
    (NOW(6), NOW(6), 'Productos para facilitar la expulsión de flemas', b'1', 'Expectorantes', 7),
    (NOW(6), NOW(6), 'Productos para descongestionar la nariz', b'1', 'Descongestionantes', 7),
    (NOW(6), NOW(6), 'Productos para aliviar el dolor de garganta', b'1', 'Cuidado de Garganta', 7),

    -- Dermatología (ID 8)
    (NOW(6), NOW(6), 'Cremas para el cuidado de la piel', b'1', 'Cremas Dermatológicas', 8),
    (NOW(6), NOW(6), 'Productos antimicóticos para la piel', b'1', 'Antimicóticos', 8),
    (NOW(6), NOW(6), 'Productos para heridas y quemaduras', b'1', 'Cicatrizantes', 8),
    (NOW(6), NOW(6), 'Productos para protección solar', b'1', 'Protectores Solares', 8),

    -- Vitaminas y Suplementos (ID 9)
    (NOW(6), NOW(6), 'Vitaminas individuales y combinadas', b'1', 'Vitaminas', 9),
    (NOW(6), NOW(6), 'Suplementos minerales', b'1', 'Minerales', 9),
    (NOW(6), NOW(6), 'Suplementos nutricionales', b'1', 'Suplementos Nutricionales', 9),
    (NOW(6), NOW(6), 'Productos con hierro y ácido fólico', b'1', 'Hierro y Ácido Fólico', 9),

    -- Primeros Auxilios (ID 10)
    (NOW(6), NOW(6), 'Gasas y vendas médicas', b'1', 'Gasas y Vendas', 10),
    (NOW(6), NOW(6), 'Apósitos y curitas', b'1', 'Apósitos', 10),
    (NOW(6), NOW(6), 'Antisépticos y desinfectantes', b'1', 'Antisépticos', 10),
    (NOW(6), NOW(6), 'Algodón y materiales de curación', b'1', 'Material de Curación', 10),

    -- Higiene Personal (ID 11)
    (NOW(6), NOW(6), 'Jabones para higiene corporal', b'1', 'Jabones', 11),
    (NOW(6), NOW(6), 'Champús y acondicionadores', b'1', 'Cuidado Capilar', 11),
    (NOW(6), NOW(6), 'Desodorantes y antitranspirantes', b'1', 'Desodorantes', 11),
    (NOW(6), NOW(6), 'Productos para higiene íntima', b'1', 'Higiene Íntima', 11),

    -- Bebés (ID 12)
    (NOW(6), NOW(6), 'Pañales para bebés', b'1', 'Pañales', 12),
    (NOW(6), NOW(6), 'Fórmulas infantiles', b'1', 'Fórmulas Infantiles', 12),
    (NOW(6), NOW(6), 'Productos para higiene del bebé', b'1', 'Higiene del Bebé', 12),
    (NOW(6), NOW(6), 'Cremas y talcos infantiles', b'1', 'Cuidado Infantil', 12),

    -- Salud Femenina (ID 13)
    (NOW(6), NOW(6), 'Productos para higiene menstrual', b'1', 'Toallas Higiénicas', 13),
    (NOW(6), NOW(6), 'Tampones y copas menstruales', b'1', 'Productos Menstruales', 13),
    (NOW(6), NOW(6), 'Pruebas de embarazo', b'1', 'Pruebas de Embarazo', 13),
    (NOW(6), NOW(6), 'Productos para cuidado femenino', b'1', 'Cuidado Femenino', 13),

    -- Salud Masculina (ID 14)
    (NOW(6), NOW(6), 'Productos para higiene masculina', b'1', 'Higiene Masculina', 14),
    (NOW(6), NOW(6), 'Productos para cuidado personal masculino', b'1', 'Cuidado Masculino', 14),
    (NOW(6), NOW(6), 'Productos de salud prostática', b'1', 'Salud Prostática', 14),
    (NOW(6), NOW(6), 'Productos de planificación familiar', b'1', 'Preservativos', 14),

    -- Equipos Médicos (ID 15)
    (NOW(6), NOW(6), 'Instrumentos para medir la presión arterial', b'1', 'Tensiómetros', 15),
    (NOW(6), NOW(6), 'Instrumentos para medir la temperatura', b'1', 'Termómetros', 15),
    (NOW(6), NOW(6), 'Equipos para medir glucosa', b'1', 'Glucómetros', 15),
    (NOW(6), NOW(6), 'Equipos para nebulización', b'1', 'Nebulizadores', 15),

    -- Salud Bucal (ID 16)
    (NOW(6), NOW(6), 'Cepillos dentales', b'1', 'Cepillos Dentales', 16),
    (NOW(6), NOW(6), 'Pastas dentales', b'1', 'Pastas Dentales', 16),
    (NOW(6), NOW(6), 'Enjuagues bucales', b'1', 'Enjuagues Bucales', 16),
    (NOW(6), NOW(6), 'Hilo dental y accesorios', b'1', 'Accesorios Dentales', 16),

    -- Salud Ocular (ID 17)
    (NOW(6), NOW(6), 'Gotas para lubricación ocular', b'1', 'Lágrimas Artificiales', 17),
    (NOW(6), NOW(6), 'Soluciones para lentes de contacto', b'1', 'Soluciones Oftálmicas', 17),
    (NOW(6), NOW(6), 'Productos para higiene ocular', b'1', 'Higiene Ocular', 17),
    (NOW(6), NOW(6), 'Accesorios para cuidado de ojos', b'1', 'Accesorios Oculares', 17),

    -- Salud Muscular (ID 18)
    (NOW(6), NOW(6), 'Cremas para dolores musculares', b'1', 'Cremas Musculares', 18),
    (NOW(6), NOW(6), 'Parches para dolores musculares', b'1', 'Parches Analgésicos', 18),
    (NOW(6), NOW(6), 'Fajas y soportes ortopédicos', b'1', 'Soportes Ortopédicos', 18),
    (NOW(6), NOW(6), 'Vendas elásticas', b'1', 'Vendas Elásticas', 18),

    -- Antialérgicos (ID 19)
    (NOW(6), NOW(6), 'Medicamentos para alergias', b'1', 'Antihistamínicos', 19),
    (NOW(6), NOW(6), 'Productos para alergias nasales', b'1', 'Antialérgicos Nasales', 19),
    (NOW(6), NOW(6), 'Productos para irritaciones de piel', b'1', 'Cuidado de Alergias Cutáneas', 19),
    (NOW(6), NOW(6), 'Productos para alergias oculares', b'1', 'Antialérgicos Oculares', 19),

    -- Productos Naturales (ID 20)
    (NOW(6), NOW(6), 'Productos elaborados con plantas medicinales', b'1', 'Plantas Medicinales', 20),
    (NOW(6), NOW(6), 'Infusiones de plantas naturales', b'1', 'Infusiones Naturales', 20),
    (NOW(6), NOW(6), 'Extractos naturales', b'1', 'Extractos Naturales', 20),
    (NOW(6), NOW(6), 'Productos tradicionales naturales', b'1', 'Productos Tradicionales', 20),

    -- Geriatría (ID 21)
    (NOW(6), NOW(6), 'Pañales para adultos', b'1', 'Pañales para Adultos', 21),
    (NOW(6), NOW(6), 'Productos para incontinencia', b'1', 'Incontinencia', 21),
    (NOW(6), NOW(6), 'Productos de apoyo para movilidad', b'1', 'Ayudas para Movilidad', 21),
    (NOW(6), NOW(6), 'Productos para cuidado de adultos mayores', b'1', 'Cuidado del Adulto Mayor', 21),

    -- Bioseguridad (ID 22)
    (NOW(6), NOW(6), 'Mascarillas de protección', b'1', 'Mascarillas', 22),
    (NOW(6), NOW(6), 'Guantes descartables', b'1', 'Guantes', 22),
    (NOW(6), NOW(6), 'Alcohol medicinal y soluciones sanitarias', b'1', 'Alcohol y Sanitizantes', 22),
    (NOW(6), NOW(6), 'Productos para desinfección', b'1', 'Desinfección', 22);