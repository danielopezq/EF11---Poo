-- 00148023 Usar la base de datos master
USE master;
GO

-- 00043823 Verificar si el inicio de sesión 'ParcialFinal' no existe
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'ParcialFinal')
BEGIN
    -- 00043823 Crear el inicio de sesión 'ParcialFinal' con la contraseña especificada
    CREATE LOGIN ParcialFinal WITH PASSWORD = 'parcialfinalpoo';
END
GO

-- 00043823 Crear la base de datos BCNN
CREATE DATABASE BCNN;
GO

-- 00043823 Usar la base de datos BCNN
USE BCNN;
GO

-- 00043823 Verificar si el usuario de base de datos 'ParcialFinal' no existe
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'ParcialFinal')
BEGIN
    -- 00043823 Crear el usuario 'ParcialFinal' para el inicio de sesión 'ParcialFinal'
    CREATE USER ParcialFinal FOR LOGIN ParcialFinal;
END
GO

-- 00043823 Agregar el usuario 'ParcialFinal' al rol 'db_datareader' para permisos de lectura
EXEC sp_addrolemember 'db_datareader', 'ParcialFinal';
-- 00043823 Agregar el usuario 'ParcialFinal' al rol 'db_datawriter' para permisos de escritura
EXEC sp_addrolemember 'db_datawriter', 'ParcialFinal';
GO


CREATE TABLE Cliente (
                         id_cliente INT PRIMARY KEY IDENTITY(1,1), -- 00148023 Campo de ID del cliente, clave primaria autoincremental
                         nombre_completo VARCHAR(100) NOT NULL, -- 00148023 Campo de nombre completo del cliente, no puede ser nulo
                         direccion VARCHAR(255), -- 00148023 Campo de dirección del cliente, puede ser nulo
                         telefono VARCHAR(15) -- 00148023 Campo de teléfono del cliente, puede ser nulo
);
GO

CREATE TABLE Tarjeta (
                         id_tarjeta INT PRIMARY KEY IDENTITY(1,1), -- 00148023 Campo de ID de la tarjeta, clave primaria autoincremental
                         id_cliente INT NOT NULL, -- 00148023 Campo de ID del cliente, no puede ser nulo
                         numero_tarjeta VARCHAR(20) NOT NULL, -- 00148023 Campo de número de tarjeta, no puede ser nulo
                         fecha_expiracion DATE NOT NULL, -- 00148023 Campo de fecha de expiración, no puede ser nulo
                         tipo_tarjeta VARCHAR(10) CHECK (tipo_tarjeta IN ('Credito', 'Debito')), -- 00148023 Campo de tipo de tarjeta, con valores permitidos
                         facilitador VARCHAR(20) NOT NULL, -- 00148023 Campo de facilitador, no puede ser nulo
                         FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) -- 00148023 Llave foránea que referencia al ID del cliente
);
GO


CREATE TABLE Compra (
                        id_compra INT PRIMARY KEY IDENTITY(1,1), -- 00148023 Campo de ID de la compra, clave primaria autoincremental
                        id_cliente INT NOT NULL, -- 00148023 Campo de ID del cliente, no puede ser nulo
                        id_tarjeta INT NOT NULL, -- 00148023 Campo de ID de la tarjeta, no puede ser nulo
                        fecha_compra DATETIME NOT NULL, -- 00148023 Campo de fecha de compra, no puede ser nulo
                        monto_total DECIMAL(10, 2) NOT NULL, -- 00148023 Campo de monto total de la compra, no puede ser nulo
                        descripcion VARCHAR(255), -- 00148023 Campo de descripción de la compra, puede ser nulo
                        FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente), -- 00148023 Llave foránea que referencia al ID del cliente
                        FOREIGN KEY (id_tarjeta) REFERENCES Tarjeta(id_tarjeta) -- 00148023 Llave foránea que referencia al ID de la tarjeta
);
GO


-- Insertar datos en la tabla Cliente
INSERT INTO Cliente (nombre_completo, direccion, telefono)
VALUES
    ('Marcelo Enrique Campos Reyes', 'Col. Escalon, San Salvador', '71353171'),
    ('Daniel Emilio Lopez Quintanilla', 'Av. Olimpica, San Salvador', '7822 3763'),
    ('Fernando Ernesto Cortez Alfaro', 'Blvd. de los Proceres, San Salvador', '6973 4276'),
    ('Roberto Steve Rosa Chavez', 'Col. San Benito, San Salvador', '7397 4266'),
    ('Gilberto Alexander Moran Cabrera', 'Col. Miramonte, San Salvador', '7382 5184'),
    ('Ronald Alejandro Amaya Landaverde', 'Col. Medica, San Salvador', '7500 4778'),
    ('Jonatan Ernesto Segura Reymundo', 'Col. Roma, San Salvador', '7004 8968'),
    ('Jonas Antonio Carrillo Carrillo', 'Col. Monte Blanco, Soyapango', '7336 5184'),
    ('Rafael Gutierrez del Barrio', 'Col. Altavista, San Salvador', '6002 0332'),
    ('Luis Alfonso Chicas Portillo', 'Col. Zacamil, San Salvador', '7843 3277');
GO

-- Insertar datos en la tabla Tarjeta
INSERT INTO Tarjeta (id_cliente, numero_tarjeta, fecha_expiracion, tipo_tarjeta, facilitador)
VALUES
    (1, '8732374898746594', '2025-12-31', 'Credito', 'American Express'),
    (2, '2920394851382949', '2025-12-31', 'Credito', 'Mastercard'),
    (3, '9384958473839387', '2025-12-31', 'Credito', 'Visa'),
    (4, '1234567899123456', '2025-11-30', 'Debito', 'Visa'),
    (5, '2345678907234567', '2026-01-31', 'Debito', 'Mastercard'),
    (6, '3456789014345678', '2026-02-28', 'Debito', 'Visa'),
    (7, '4567890121456789', '2025-10-31', 'Credito', 'American Express'),
    (8, '5678901232567890', '2026-03-31', 'Credito', 'Visa'),
    (9, '6789012345638901', '2025-08-31', 'Debito', 'Mastercard'),
    (10, '7890123456787012', '2026-04-30', 'Credito', 'Visa');
GO

-- Insertar datos en la tabla Compra
INSERT INTO Compra (id_cliente, id_tarjeta, fecha_compra, monto_total, descripcion)
VALUES
    (1, 1, CONVERT(DATETIME, '2024-07-09 12:00:00', 120), 5000.00, 'Reloj de lujo'),
    (1, 1, CONVERT(DATETIME, '2024-01-28 13:00:00', 120), 200000.00, 'Carro de lujo'),
    (1, 1, CONVERT(DATETIME, '2024-03-30 14:00:00', 120), 30.00, 'Skin de Call of Duty'),
    (1, 1, CONVERT(DATETIME, '2024-11-15 15:00:00', 120), 10000.00, 'Joyas exclusivas'),
    (1, 1, CONVERT(DATETIME, '2024-12-05 16:00:00', 120), 7000.00, 'Boleto para partido del Real Madrid'),
    (2, 2, CONVERT(DATETIME, '2024-09-06 17:00:00', 120), 150.00, 'Ropa'),
    (3, 3, CONVERT(DATETIME, '2024-09-26 18:00:00', 120), 80.00, 'Suscripcion'),
    (4, 4, CONVERT(DATETIME, '2024-04-17 19:00:00', 120), 50.00, 'Videojuego'),
    (5, 5, CONVERT(DATETIME, '2024-03-13 20:00:00', 120), 200.00, 'Lavadora'),
    (6, 6, CONVERT(DATETIME, '2024-06-10 21:00:00', 120), 900.00, 'Pc gamer'),
    (7, 7, CONVERT(DATETIME, '2024-02-21 22:00:00', 120), 285.00, 'Locion'),
    (8, 8, CONVERT(DATETIME, '2024-04-23 23:00:00', 120), 800.00, 'Celular'),
    (9, 9, CONVERT(DATETIME, '2024-05-01 12:00:00', 120), 1200.00, 'Pantalla LG'),
    (10, 10, CONVERT(DATETIME,'2024-09-03 13:00:00', 120), 40.00, 'Libro');
GO


