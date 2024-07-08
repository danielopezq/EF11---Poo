CREATE database BCN;
USE BCN;

CREATE TABLE Cliente (
                         id_cliente INT PRIMARY KEY IDENTITY(1,1),
                         nombre_completo VARCHAR(100) NOT NULL,
                         direccion VARCHAR(255),
                         telefono VARCHAR(15)
);

CREATE TABLE Tarjeta (
                         id_tarjeta INT PRIMARY KEY IDENTITY(1,1),
                         id_cliente INT NOT NULL,
                         numero_tarjeta VARCHAR(20) NOT NULL,
                         fecha_expiracion DATE NOT NULL,
                         tipo_tarjeta VARCHAR(10) CHECK (tipo_tarjeta IN ('Crédito', 'Débito')),
                         facilitador VARCHAR(20) NOT NULL,
                         FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Compra (
                        id_compra INT PRIMARY KEY IDENTITY(1,1),
                        id_cliente INT NOT NULL,
                        id_tarjeta INT NOT NULL,
                        fecha_compra DATETIME NOT NULL,
                        monto_total DECIMAL(10, 2) NOT NULL,
                        descripcion VARCHAR(255),
                        FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
                        FOREIGN KEY (id_tarjeta) REFERENCES Tarjeta(id_tarjeta)
);

-- Insert data into Cliente
INSERT INTO Cliente (nombre_completo, direccion, telefono)
VALUES
    ('Marcelo Enrique Campos Reyes', 'Col. Escalón, San Salvador', '71353171'),
    ('Daniel Emilio López Quintanilla', 'Av. Olímpica, San Salvador', '7822 3763'),
    ('Fernando Ernesto Cortez Alfaro', 'Blvd. de los Próceres, San Salvador', '6973 4276'),
    ('Roberto Steve Rosa Chávez', 'Col. San Benito, San Salvador', '7397 4266'),
    ('Gilberto Alexander Moran Cabrera', 'Col. Miramonte, San Salvador', '7382 5184'),
    ('Ronald Alejandro Amaya Landaverde', 'Col. Medica, San Salvador', '7500 4778'),
    ('Jonatan Ernesto Segura Reymundo', 'Col. Roma, San Salvador', '7004 8968'),
    ('Jonas Antonio Carrillo Carrillo', 'Col. Monte Blanco, Soyapango', '7336 5184'),
    ('Rafael Gutiérrez del Barrio', 'Col. Altavista, San Salvador', '6002 0332'),
    ('Luis Alfonso Chicas Portillo', 'Col. Zacamil, San Salvador', '7843 3277');

-- Insert data into Tarjeta
INSERT INTO Tarjeta (id_cliente, numero_tarjeta, fecha_expiracion, tipo_tarjeta, facilitador)
VALUES
    (1, '8732374898748594', '2025-12-31', 'Crédito', 'American Express'),
    (2, '2920394857382949', '2025-12-31', 'Crédito', 'Mastercard'),
    (3, '9384958473839387', '2025-12-31', 'Crédito', 'Visa'),
    (4, '1234567890123456', '2025-11-30', 'Débito', 'Visa'),
    (5, '2345678901234567', '2026-01-31', 'Crédito', 'Mastercard'),
    (6, '3456789012345678', '2026-02-28', 'Débito', 'Visa'),
    (7, '4567890123456789', '2025-10-31', 'Crédito', 'American Express'),
    (8, '5678901234567890', '2026-03-31', 'Crédito', 'Visa'),
    (9, '6789012345678901', '2025-08-31', 'Débito', 'Mastercard'),
    (10, '7890123456789012', '2026-04-30', 'Crédito', 'Visa');

-- Insert data into Compra
INSERT INTO Compra (id_cliente, id_tarjeta, fecha_compra, monto_total, descripcion)
VALUES
    (1, 1, CONVERT(DATETIME, '2024-07-01 12:00:00', 120), 5000.00, 'Reloj de lujo'),
    (1, 1, CONVERT(DATETIME, '2024-07-02 13:00:00', 120), 200000.00, 'Carro de lujo'),
    (1, 1, CONVERT(DATETIME, '2024-07-03 14:00:00', 120), 30.00, 'Skin de Call of Duty'),
    (1, 1, CONVERT(DATETIME, '2024-07-04 15:00:00', 120), 10000.00, 'Joyas exclusivas'),
    (1, 1, CONVERT(DATETIME, '2024-07-05 16:00:00', 120), 7000.00, 'Boleto para partido del Real Madrid'),
    (2, 2, CONVERT(DATETIME, '2024-07-06 17:00:00', 120), 150.00, 'Ropa'),
    (3, 3, CONVERT(DATETIME, '2024-07-07 18:00:00', 120), 80.00, 'Suscripción'),
    (4, 4, CONVERT(DATETIME, '2024-07-08 19:00:00', 120), 50.00, 'Videojuego'),
    (5, 5, CONVERT(DATETIME, '2024-07-09 20:00:00', 120), 200.00, 'Lavadora'),
    (6, 6, CONVERT(DATETIME, '2024-07-10 21:00:00', 120), 900.00, 'Pc gamer'),
    (7, 7, CONVERT(DATETIME, '2024-07-11 22:00:00', 120), 285.00, 'loción'),
    (8, 8, CONVERT(DATETIME, '2024-07-12 23:00:00', 120), 800.00, 'Celular'),
    (9, 9, CONVERT(DATETIME, '2024-07-13 12:00:00', 120), 1200.00, 'Pantalla LG'),
    (10, 10, CONVERT(DATETIME, '2024-07-14 13:00:00', 120), 40.00, 'Libro');

