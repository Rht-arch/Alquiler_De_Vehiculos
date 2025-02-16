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

-- Inserción de vehículos (Coches)
INSERT INTO vehiculos (marca, modelo, año, tipo, precio_dia) VALUES
                                                                 ('Toyota', 'Corolla', 2015, 'Coche', 50.0),
                                                                 ('Toyota', 'Camry', 2016, 'Coche', 55.0),
                                                                 ('Toyota', 'Rav4', 2017, 'Coche', 60.0),
                                                                 ('Toyota', 'Yaris', 2018, 'Coche', 45.0),
                                                                 ('Ford', 'Focus', 2016, 'Coche', 50.0),
                                                                 ('Ford', 'Mustang', 2017, 'Coche', 80.0),
                                                                 ('Ford', 'Explorer', 2018, 'Coche', 70.0),
                                                                 ('Ford', 'F-150', 2019, 'Coche', 90.0),
                                                                 ('BMW', 'Serie 3', 2017, 'Coche', 65.0),
                                                                 ('BMW', 'X5', 2018, 'Coche', 85.0),
                                                                 ('BMW', 'M4', 2019, 'Coche', 100.0),
                                                                 ('BMW', 'i8', 2020, 'Coche', 120.0),
                                                                 ('Honda', 'Civic', 2016, 'Coche', 55.0),
                                                                 ('Honda', 'CR-V', 2017, 'Coche', 60.0),
                                                                 ('Honda', 'Accord', 2018, 'Coche', 65.0),
                                                                 ('Honda', 'HR-V', 2019, 'Coche', 50.0),
                                                                 ('Volkswagen', 'Golf', 2015, 'Coche', 50.0),
                                                                 ('Volkswagen', 'Passat', 2016, 'Coche', 55.0),
                                                                 ('Volkswagen', 'Tiguan', 2017, 'Coche', 60.0),
                                                                 ('Volkswagen', 'Polo', 2018, 'Coche', 45.0),
                                                                 ('Audi', 'A3', 2017, 'Coche', 60.0),
                                                                 ('Audi', 'A4', 2018, 'Coche', 70.0),
                                                                 ('Audi', 'Q5', 2019, 'Coche', 80.0),
                                                                 ('Audi', 'R8', 2020, 'Coche', 150.0),
                                                                 ('Mercedes-Benz', 'Clase C', 2017, 'Coche', 70.0),
                                                                 ('Mercedes-Benz', 'Clase E', 2018, 'Coche', 85.0),
                                                                 ('Mercedes-Benz', 'GLA', 2019, 'Coche', 75.0),
                                                                 ('Mercedes-Benz', 'GLE', 2020, 'Coche', 90.0),
                                                                 ('Nissan', 'Altima', 2016, 'Coche', 55.0),
                                                                 ('Nissan', 'Sentra', 2017, 'Coche', 50.0),
                                                                 ('Nissan', 'X-Trail', 2018, 'Coche', 60.0),
                                                                 ('Nissan', 'GT-R', 2019, 'Coche', 120.0),
                                                                 ('Peugeot', '208', 2015, 'Coche', 45.0),
                                                                 ('Peugeot', '3008', 2016, 'Coche', 55.0),
                                                                 ('Peugeot', '508', 2017, 'Coche', 60.0),
                                                                 ('Peugeot', '2008', 2018, 'Coche', 50.0),
                                                                 ('Chevrolet', 'Cruze', 2016, 'Coche', 50.0),
                                                                 ('Chevrolet', 'Camaro', 2017, 'Coche', 90.0),
                                                                 ('Chevrolet', 'Silverado', 2018, 'Coche', 100.0),
                                                                 ('Chevrolet', 'Tracker', 2019, 'Coche', 60.0),
                                                                 ('Renault', 'Clio', 2015, 'Coche', 40.0),
                                                                 ('Renault', 'Megane', 2016, 'Coche', 50.0),
                                                                 ('Renault', 'Kangoo', 2017, 'Coche', 55.0),
                                                                 ('Renault', 'Duster', 2018, 'Coche', 60.0),
                                                                 ('Fiat', '500', 2015, 'Coche', 40.0),
                                                                 ('Fiat', 'Panda', 2016, 'Coche', 35.0),
                                                                 ('Fiat', 'Ducato', 2017, 'Coche', 70.0),
                                                                 ('Fiat', 'Tipo', 2018, 'Coche', 45.0);

-- Inserción de vehículos (Motos)
INSERT INTO vehiculos (marca, modelo, año, tipo, precio_dia) VALUES
                                                                 ('Harley-Davidson', 'Sportster', 2015, 'Moto', 80.0),
                                                                 ('Harley-Davidson', 'Softail', 2016, 'Moto', 90.0),
                                                                 ('Harley-Davidson', 'Road Glide', 2017, 'Moto', 100.0),
                                                                 ('Harley-Davidson', 'Street Bob', 2018, 'Moto', 85.0),
                                                                 ('Yamaha', 'R1', 2015, 'Moto', 70.0),
                                                                 ('Yamaha', 'MT-09', 2016, 'Moto', 60.0),
                                                                 ('Yamaha', 'Tenere 700', 2017, 'Moto', 65.0),
                                                                 ('Yamaha', 'XSR900', 2018, 'Moto', 70.0),
                                                                 ('Ducati', 'Panigale', 2015, 'Moto', 120.0),
                                                                 ('Ducati', 'Monster', 2016, 'Moto', 90.0),
                                                                 ('Ducati', 'Multistrada', 2017, 'Moto', 100.0),
                                                                 ('Ducati', 'Scrambler', 2018, 'Moto', 85.0),
                                                                 ('Kawasaki', 'Ninja 400', 2015, 'Moto', 60.0),
                                                                 ('Kawasaki', 'Z900', 2016, 'Moto', 70.0),
                                                                 ('Kawasaki', 'Versys', 2017, 'Moto', 65.0),
                                                                 ('Kawasaki', 'H2', 2018, 'Moto', 150.0),
                                                                 ('Suzuki', 'GSX-R1000', 2015, 'Moto', 80.0),
                                                                 ('Suzuki', 'Hayabusa', 2016, 'Moto', 100.0),
                                                                 ('Suzuki', 'V-Strom', 2017, 'Moto', 70.0),
                                                                 ('Suzuki', 'SV650', 2018, 'Moto', 60.0),
                                                                 ('Triumph', 'Bonneville', 2015, 'Moto', 75.0),
                                                                 ('Triumph', 'Tiger 900', 2016, 'Moto', 85.0),
                                                                 ('Triumph', 'Rocket 3', 2017, 'Moto', 120.0),
                                                                 ('Triumph', 'Street Triple', 2018, 'Moto', 90.0),
                                                                 ('Piaggio', 'Vespa Primavera', 2015, 'Moto', 40.0),
                                                                 ('Piaggio', 'Beverly', 2016, 'Moto', 45.0),
                                                                 ('Piaggio', 'Medley', 2017, 'Moto', 50.0),
                                                                 ('Piaggio', 'Liberty', 2018, 'Moto', 45.0),
                                                                 ('KTM', 'Duke 390', 2015, 'Moto', 55.0),
                                                                 ('KTM', 'RC 200', 2016, 'Moto', 50.0),
                                                                 ('KTM', 'Super Adventure', 2017, 'Moto', 80.0),
                                                                 ('KTM', '690 SMC R', 2018, 'Moto', 70.0);

-- Inserción de vehículos (Furgonetas/Camiones)
INSERT INTO vehiculos (marca, modelo, año, tipo, precio_dia) VALUES
                                                                 ('Citroën', 'Berlingo', 2015, 'Furgoneta/Camión', 60.0),
                                                                 ('Citroën', 'Jumpy', 2016, 'Furgoneta/Camión', 70.0),
                                                                 ('Citroën', 'Spacetourer', 2017, 'Furgoneta/Camión', 80.0),
                                                                 ('Citroën', 'Jumper', 2018, 'Furgoneta/Camión', 90.0),
                                                                 ('Opel', 'Vivaro', 2015, 'Furgoneta/Camión', 65.0),
                                                                 ('Opel', 'Combo', 2016, 'Furgoneta/Camión', 55.0),
                                                                 ('Opel', 'Movano', 2017, 'Furgoneta/Camión', 75.0),
                                                                 ('Opel', 'Zafira Life', 2018, 'Furgoneta/Camión', 70.0),
                                                                 ('Mercedes-Benz', 'Vito', 2015, 'Furgoneta/Camión', 80.0),
                                                                 ('Mercedes-Benz', 'Sprinter', 2016, 'Furgoneta/Camión', 90.0),
                                                                 ('Mercedes-Benz', 'Citan', 2017, 'Furgoneta/Camión', 70.0),
                                                                 ('Mercedes-Benz', 'eSprinter', 2018, 'Furgoneta/Camión', 100.0),
                                                                 ('Iveco', 'Daily', 2015, 'Furgoneta/Camión', 85.0),
                                                                 ('Iveco', 'Eurocargo', 2016, 'Furgoneta/Camión', 95.0),
                                                                 ('Iveco', 'Stralis', 2017, 'Furgoneta/Camión', 120.0),
                                                                 ('Iveco', 'S-Way', 2018, 'Furgoneta/Camión', 130.0),
                                                                 ('MAN', 'TGL', 2015, 'Furgoneta/Camión', 100.0),
                                                                 ('MAN', 'TGM', 2016, 'Furgoneta/Camión', 110.0),
                                                                 ('MAN', 'TGX', 2017, 'Furgoneta/Camión', 120.0),
                                                                 ('MAN', 'TGS', 2018, 'Furgoneta/Camión', 130.0),
                                                                 ('Scania', 'P-Series', 2015, 'Furgoneta/Camión', 110.0),
                                                                 ('Scania', 'G-Series', 2016, 'Furgoneta/Camión', 120.0),
                                                                 ('Scania', 'R-Series', 2017, 'Furgoneta/Camión', 130.0),
                                                                 ('Scania', 'S-Series', 2018, 'Furgoneta/Camión', 140.0);

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
