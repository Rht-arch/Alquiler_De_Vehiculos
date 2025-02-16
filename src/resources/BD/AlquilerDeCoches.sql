CREATE DATABASE IF NOT EXISTS alquiler_vehiculos_db;
USE alquiler_vehiculos_db;

-- =========================
-- 📌 TABLAS PRINCIPALES
-- =========================
CREATE TABLE IF NOT EXISTS clientes (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        dni VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    contraseña VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS vehiculos (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    año INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    precio_dia FLOAT NOT NULL
    );

CREATE TABLE IF NOT EXISTS alquileresDetalles (
                                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                                  id_cliente INT NOT NULL,
                                                  id_vehiculo INT NOT NULL,
                                                  marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    año INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    total FLOAT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id) ON DELETE CASCADE
    );

-- =========================
-- 📌 LIMPIAR DATOS
-- =========================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE alquileresDetalles;
TRUNCATE TABLE vehiculos;
SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- 📌 INSERTAR NUEVOS VEHÍCULOS
-- =========================
INSERT INTO vehiculos (marca, modelo, año, tipo, precio_dia) VALUES
-- 🚗 Coches
('Toyota', 'Corolla', 2022, 'Coche', 50.0),
('Toyota', 'Camry', 2021, 'Coche', 60.0),
('Ford', 'Focus', 2023, 'Coche', 55.0),
('Ford', 'Mustang', 2020, 'Coche', 90.0),
('BMW', 'Serie 3', 2022, 'Coche', 80.0),
('BMW', 'X5', 2021, 'Coche', 100.0),
('Honda', 'Civic', 2023, 'Coche', 50.0),
('Honda', 'CR-V', 2021, 'Coche', 65.0),
('Volkswagen', 'Golf', 2022, 'Coche', 55.0),
('Volkswagen', 'Passat', 2021, 'Coche', 60.0),

-- 🏍️ Motos
('Harley-Davidson', 'Sportster', 2021, 'Moto', 70.0),
('Yamaha', 'R1', 2022, 'Moto', 85.0),
('Ducati', 'Panigale', 2023, 'Moto', 100.0),
('Kawasaki', 'Ninja 400', 2021, 'Moto', 65.0),
('Suzuki', 'GSX-R1000', 2022, 'Moto', 80.0),

-- 🚛 Camiones y Furgonetas
('Mercedes-Benz', 'Sprinter', 2022, 'Furgoneta/Camión', 110.0),
('Iveco', 'Daily', 2021, 'Furgoneta/Camión', 120.0),
('MAN', 'TGL', 2022, 'Furgoneta/Camión', 130.0),
('Scania', 'R-Series', 2023, 'Furgoneta/Camión', 140.0);

-- =========================
-- 📌 MANTENER CLIENTES
-- =========================
INSERT INTO clientes (dni, nombre, apellido, telefono, correo, contraseña) VALUES
                                                                               ('12345678A', 'Juan', 'Pérez', '600123456', 'juan.perez@gmail.com', 'password123'),
                                                                               ('23456789B', 'María', 'Gómez', '611234567', 'maria.gomez@gmail.com', 'securePass456'),
                                                                               ('34567890C', 'Carlos', 'López', '622345678', 'carlos.lopez@gmail.com', 'myPassword789'),
                                                                               ('45678901D', 'Laura', 'Fernández', '633456789', 'laura.fernandez@gmail.com', 'lauraPass321'),
                                                                               ('00000000X', 'Admin', 'Admin', '644567890', 'admin@gmail.com', 'admin123')
    ON DUPLICATE KEY UPDATE dni = VALUES(dni); -- Evita duplicados

