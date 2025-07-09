CREATE DATABASE IF NOT EXISTS bd_mascotas;
USE bd_mascotas;

CREATE TABLE personas (
    documento VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL
);

CREATE TABLE mascotas (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    id_dueno VARCHAR(20) NOT NULL,
    raza VARCHAR(50) NOT NULL,
    sexo ENUM('Macho', 'Hembra') NOT NULL,
    FOREIGN KEY (id_dueno) REFERENCES personas(documento) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
);

INSERT INTO personas (documento, nombre, telefono) VALUES
('12345678', 'María González López', '3001234567'),
('87654321', 'Carlos Rodríguez Pérez', '3109876543'),
('11223344', 'Ana Martínez Silva', '3155551234'),
('55667788', 'Luis Fernández Torres', '3208889999'),
('99887766', 'Carmen Vargas Ruiz', '3114447777'),
('33445566', 'Roberto Jiménez Díaz', '3146668888'),
('77889900', 'Patricia Morales Vega', '3137779999'),
('11223355', 'Diego Herrera Castro', '3168880000');

INSERT INTO mascotas (nombre, id_dueno, raza, sexo) VALUES
('Luna', '12345678', 'Golden Retriever', 'Hembra'),
('Max', '12345678', 'Labrador', 'Macho'),
('Bella', '87654321', 'Poodle', 'Hembra'),
('Rocky', '11223344', 'Pastor Alemán', 'Macho'),
('Mia', '11223344', 'Siamesa', 'Hembra'),
('Thor', '55667788', 'Husky Siberiano', 'Macho'),
('Nina', '99887766', 'Bulldog Francés', 'Hembra'),
('Simba', '33445566', 'Persa', 'Macho'),
('Lola', '77889900', 'Chihuahua', 'Hembra'),
('Toby', '11223355', 'Beagle', 'Macho'),
('Kitty', '11223355', 'Maine Coon', 'Hembra');

SELECT 
    p.documento,
    p.nombre AS nombre_persona,
    p.telefono,
    COUNT(m.id_mascota) AS total_mascotas
FROM personas p
LEFT JOIN mascotas m ON p.documento = m.id_dueno
GROUP BY p.documento, p.nombre, p.telefono
ORDER BY p.nombre;

SELECT 
    m.nombre AS nombre_mascota,
    m.raza,
    m.sexo,
    p.nombre AS nombre_dueno,
    p.telefono AS telefono_dueno
FROM mascotas m
JOIN personas p ON m.id_dueno = p.documento
ORDER BY m.nombre;

SELECT 
    raza,
    COUNT(*) AS cantidad,
    COUNT(CASE WHEN sexo = 'Macho' THEN 1 END) AS machos,
    COUNT(CASE WHEN sexo = 'Hembra' THEN 1 END) AS hembras
FROM mascotas
GROUP BY raza
ORDER BY cantidad DESC;

SELECT 
    p.nombre AS nombre_persona,
    p.telefono,
    COUNT(m.id_mascota) AS total_mascotas
FROM personas p
JOIN mascotas m ON p.documento = m.id_dueno
GROUP BY p.documento, p.nombre, p.telefono
HAVING COUNT(m.id_mascota) > 1
ORDER BY total_mascotas DESC;

CREATE VIEW vista_mascotas_completa AS
SELECT 
    m.id_mascota,
    m.nombre AS nombre_mascota,
    m.raza,
    m.sexo,
    p.documento AS documento_dueno,
    p.nombre AS nombre_dueno,
    p.telefono AS telefono_dueno
FROM mascotas m
JOIN personas p ON m.id_dueno = p.documento;

CREATE VIEW vista_resumen_duenos AS
SELECT 
    p.documento,
    p.nombre,
    p.telefono,
    COUNT(m.id_mascota) AS total_mascotas,
    COUNT(CASE WHEN m.sexo = 'Macho' THEN 1 END) AS mascotas_machos,
    COUNT(CASE WHEN m.sexo = 'Hembra' THEN 1 END) AS mascotas_hembras
FROM personas p
LEFT JOIN mascotas m ON p.documento = m.id_dueno
GROUP BY p.documento, p.nombre, p.telefono;

CREATE INDEX idx_dueno ON mascotas(id_dueno);
CREATE INDEX idx_nombre_mascota ON mascotas(nombre);
CREATE INDEX idx_raza ON mascotas(raza);
CREATE INDEX idx_sexo ON mascotas(sexo);
CREATE INDEX idx_nombre_persona ON personas(nombre);

SELECT 'Base de datos BD_MASCOTAS creada exitosamente' AS mensaje;
SELECT 'Tablas creadas: personas, mascotas' AS tablas;
SELECT 'Datos de prueba insertados' AS datos;
SELECT 'Vistas creadas' AS vistas; 