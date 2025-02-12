CREATE DATABASE IF NOT EXISTS alquiler_vehiculos_db;
USE alquiler_vehiculos_db;

CREATE TABLE clientes (
    contraseña VARCHAR(255) NOT NULL,
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100) NOT NULL
);

CREATE TABLE vehiculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    año INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    precio_dia FLOAT NOT NULL
);

CREATE TABLE alquileres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_vehiculo INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    total FLOAT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id) ON DELETE CASCADE
);

-- Inserción de vehículos
INSERT INTO vehiculos (marca, modelo, año, tipo, precio_dia) VALUES

-- Coches
('Toyota', 'Corolla', 2015, 'Coche', 50.0),
('Toyota', 'Camry', 2018, 'Coche', 55.0),
('Toyota', 'Rav4', 2020, 'Coche', 60.0),
('Toyota', 'Yaris', 2012, 'Coche', 45.0),

('Ford', 'Focus', 2016, 'Coche', 50.0),
('Ford', 'Mustang', 2019, 'Coche', 100.0),
('Ford', 'Explorer', 2021, 'Coche', 75.0),
('Ford', 'F-150', 2013, 'Coche', 80.0),

('BMW', 'Serie 3', 2017, 'Coche', 65.0),
('BMW', 'X5', 2014, 'Coche', 90.0),
('BMW', 'M4', 2022, 'Coche', 120.0),
('BMW', 'i8', 2011, 'Coche', 150.0),

('Honda', 'Civic', 2016, 'Coche', 55.0),
('Honda', 'CR-V', 2019, 'Coche', 60.0),
('Honda', 'Accord', 2023, 'Coche', 65.0),
('Honda', 'HR-V', 2010, 'Coche', 50.0),

('Volkswagen', 'Golf', 2015, 'Coche', 50.0),
('Volkswagen', 'Passat', 2018, 'Coche', 55.0),
('Volkswagen', 'Tiguan', 2024, 'Coche', 60.0),
('Volkswagen', 'Polo', 2011, 'Coche', 45.0),

('Audi', 'A3', 2017, 'Coche', 60.0),
('Audi', 'A4', 2014, 'Coche', 65.0),
('Audi', 'Q5', 2022, 'Coche', 70.0),
('Audi', 'R8', 2010, 'Coche', 150.0),

('Mercedes-Benz', 'Clase C', 2017, 'Coche', 70.0),
('Mercedes-Benz', 'Clase E', 2020, 'Coche', 75.0),
('Mercedes-Benz', 'GLA', 2023, 'Coche', 80.0),
('Mercedes-Benz', 'GLE', 2012, 'Coche', 85.0),

('Nissan', 'Altima', 2016, 'Coche', 55.0),
('Nissan', 'Sentra', 2019, 'Coche', 50.0),
('Nissan', 'X-Trail', 2021, 'Coche', 60.0),
('Nissan', 'GT-R', 2011, 'Coche', 120.0),

('Peugeot', '208', 2015, 'Coche', 45.0),
('Peugeot', '3008', 2018, 'Coche', 50.0),
('Peugeot', '508', 2024, 'Coche', 65.0),
('Peugeot', '2008', 2013, 'Coche', 55.0),

('Chevrolet', 'Cruze', 2016, 'Coche', 50.0),
('Chevrolet', 'Camaro', 2019, 'Coche', 90.0),
('Chevrolet', 'Silverado', 2022, 'Coche', 80.0),
('Chevrolet', 'Tracker', 2010, 'Coche', 60.0),

('Renault', 'Clio', 2014, 'Coche', 45.0),
('Renault', 'Megane', 2017, 'Coche', 50.0),
('Renault', 'Kangoo', 2021, 'Coche', 55.0),
('Renault', 'Duster', 2012, 'Coche', 60.0),

('Fiat', '500', 2015, 'Coche', 40.0),
('Fiat', 'Panda', 2018, 'Coche', 42.0),
('Fiat', 'Ducato', 2023, 'Coche', 70.0),
('Fiat', 'Tipo', 2011, 'Coche', 50.0);


-- Motos
('Harley-Davidson', 'Sportster', 2013, 'Moto', 40.0),
('Harley-Davidson', 'Softail', 2016, 'Moto', 45.0),
('Harley-Davidson', 'Road Glide', 2024, 'Moto', 50.0),
('Harley-Davidson', 'Street Bob', 2012, 'Moto', 42.0),

('Yamaha', 'R1', 2015, 'Moto', 55.0),
('Yamaha', 'MT-09', 2018, 'Moto', 50.0),
('Yamaha', 'Tenere 700', 2021, 'Moto', 48.0),
('Yamaha', 'XSR900', 2010, 'Moto', 46.0),

('Ducati', 'Panigale', 2014, 'Moto', 60.0),
('Ducati', 'Monster', 2017, 'Moto', 55.0),
('Ducati', 'Multistrada', 2023, 'Moto', 65.0),
('Ducati', 'Scrambler', 2011, 'Moto', 50.0),

('Kawasaki', 'Ninja 400', 2017, 'Moto', 55.0),
('Kawasaki', 'Z900', 2020, 'Moto', 60.0),
('Kawasaki', 'Versys', 2023, 'Moto', 65.0),
('Kawasaki', 'H2', 2012, 'Moto', 90.0),

('Suzuki', 'GSX-R1000', 2016, 'Moto', 60.0),
('Suzuki', 'Hayabusa', 2019, 'Moto', 75.0),
('Suzuki', 'V-Strom', 2021, 'Moto', 50.0),
('Suzuki', 'SV650', 2011, 'Moto', 45.0),

('Triumph', 'Bonneville', 2015, 'Moto', 50.0),
('Triumph', 'Tiger 900', 2018, 'Moto', 55.0),
('Triumph', 'Rocket 3', 2024, 'Moto', 85.0),
('Triumph', 'Street Triple', 2013, 'Moto', 65.0),

('Piaggio', 'Vespa Primavera', 2014, 'Moto', 40.0),
('Piaggio', 'Beverly', 2017, 'Moto', 45.0),
('Piaggio', 'Medley', 2021, 'Moto', 48.0),
('Piaggio', 'Liberty', 2010, 'Moto', 42.0),

('KTM', 'Duke 390', 2015, 'Moto', 55.0),
('KTM', 'RC 200', 2018, 'Moto', 50.0),
('KTM', 'Super Adventure', 2023, 'Moto', 70.0),
('KTM', '690 SMC R', 2012, 'Moto', 60.0);


-- Furgonetas/Camiones
('Citroën', 'Berlingo', 2016, 'Furgoneta/Camión', 65.0),
('Citroën', 'Jumpy', 2019, 'Furgoneta/Camión', 70.0),
('Citroën', 'Spacetourer', 2022, 'Furgoneta/Camión', 75.0),
('Citroën', 'Jumper', 2010, 'Furgoneta/Camión', 80.0),

('Opel', 'Vivaro', 2013, 'Furgoneta/Camión', 68.0),
('Opel', 'Combo', 2015, 'Furgoneta/Camión', 63.0),
('Opel', 'Movano', 2024, 'Furgoneta/Camión', 78.0),
('Opel', 'Zafira Life', 2012, 'Furgoneta/Camión', 72.0),

('Mercedes-Benz', 'Vito', 2017, 'Furgoneta/Camión', 85.0),
('Mercedes-Benz', 'Sprinter', 2014, 'Furgoneta/Camión', 90.0),
('Mercedes-Benz', 'Citan', 2021, 'Furgoneta/Camión', 70.0),
('Mercedes-Benz', 'eSprinter', 2010, 'Furgoneta/Camión', 95.0),

('Iveco', 'Daily', 2015, 'Furgoneta/Camión', 88.0),
('Iveco', 'Eurocargo', 2018, 'Furgoneta/Camión', 95.0),
('Iveco', 'Stralis', 2023, 'Furgoneta/Camión', 110.0),
('Iveco', 'S-Way', 2011, 'Furgoneta/Camión', 100.0),

('MAN', 'TGL', 2016, 'Furgoneta/Camión', 105.0),
('MAN', 'TGM', 2019, 'Furgoneta/Camión', 110.0),
('MAN', 'TGX', 2022, 'Furgoneta/Camión', 120.0),
('MAN', 'TGS', 2013, 'Furgoneta/Camión', 115.0),

('Scania', 'P-Series', 2014, 'Furgoneta/Camión', 130.0),
('Scania', 'G-Series', 2017, 'Furgoneta/Camión', 135.0),
('Scania', 'R-Series', 2021, 'Furgoneta/Camión', 140.0),
('Scania', 'S-Series', 2010, 'Furgoneta/Camión', 145.0);


-- Inserción de usuarios
INSERT INTO usuarios (nombre, apellido, correo, contraseña) VALUES
('Juan', 'Pérez', 'juan.perez@gmail.com', 'password123'),
('María', 'Gómez', 'maria.gomez@gmail.com', 'securePass456'),
('Carlos', 'López', 'carlos.lopez@gmail.com', 'myPassword789'),
('Laura', 'Fernández', 'laura.fernandez@gmail.com', 'lauraPass321'),
('Admin','Admin','admin@gmail.com','admin123');