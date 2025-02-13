CREATE DATABASE IF NOT EXISTS alquiler_vehiculos_db;
USE alquiler_vehiculos_db;

CREATE TABLE clientes (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          dni VARCHAR(20) NOT NULL,
                          nombre VARCHAR(100) NOT NULL,
                          apellido VARCHAR(100) NOT NULL,
                          telefono VARCHAR(20) NOT NULL,
                          correo VARCHAR(100) NOT NULL,
                          contraseña VARCHAR(255) NOT NULL
);

CREATE TABLE vehiculos (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           marca VARCHAR(100) NOT NULL,
                           modelo VARCHAR(100) NOT NULL,
                           año INT NOT NULL,
                           tipo VARCHAR(50) NOT NULL,
                           precio_dia FLOAT NOT NULL
);

CREATE TABLE alquileresDetalles (
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

-- Inserción de vehículos
INSERT INTO vehiculos (marca, modelo, año, tipo, precio_dia) VALUES
                                                                 ('Toyota', 'Corolla', 2015, 'Coche', 50.0),
                                                                 ('Ford', 'Focus', 2016, 'Coche', 50.0),
                                                                 ('BMW', 'Serie 3', 2017, 'Coche', 65.0),
                                                                 ('Honda', 'Civic', 2016, 'Coche', 55.0),
                                                                 ('Volkswagen', 'Golf', 2015, 'Coche', 50.0),
                                                                 ('Audi', 'A3', 2017, 'Coche', 60.0),
                                                                 ('Mercedes-Benz', 'Clase C', 2017, 'Coche', 70.0),
                                                                 ('Nissan', 'Altima', 2016, 'Coche', 55.0),
                                                                 ('Peugeot', '208', 2015, 'Coche', 45.0),
                                                                 ('Chevrolet', 'Cruze', 2016, 'Coche', 50.0);

-- Inserción de clientes
INSERT INTO clientes (dni, nombre, apellido, telefono, correo, contraseña) VALUES
                                                                               ('12345678A', 'Juan', 'Pérez', '600123456', 'juan.perez@gmail.com', 'password123'),
                                                                               ('23456789B', 'María', 'Gómez', '611234567', 'maria.gomez@gmail.com', 'securePass456'),
                                                                               ('34567890C', 'Carlos', 'López', '622345678', 'carlos.lopez@gmail.com', 'myPassword789'),
                                                                               ('45678901D', 'Laura', 'Fernández', '633456789', 'laura.fernandez@gmail.com', 'lauraPass321'),
                                                                               ('00000000X', 'Admin', 'Admin', '644567890', 'admin@gmail.com', 'admin123');

-- Ejemplo de inserción en alquileresDetalles
INSERT INTO alquileresDetalles (id_cliente, id_vehiculo, marca, modelo, año, tipo, fecha_inicio, fecha_fin, total) VALUES
                                                                                                                       (1, 1, 'Toyota', 'Corolla', 2015, 'Coche', '2024-02-01', '2024-02-05', 200.0),
                                                                                                                       (2, 3, 'BMW', 'Serie 3', 2017, 'Coche', '2024-03-10', '2024-03-15', 325.0);
