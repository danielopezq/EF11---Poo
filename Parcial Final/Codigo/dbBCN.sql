USE master; --00043823 Me dirijo a la base de datos llamada master
GO --00043823 Esto se usa para que se ejecute la query y luego siga con las demás querys

IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'ParcialFinal') --00043823 Primero verifico si no existe el usuario llamado ParcialFinal
BEGIN --00043823 Se utiliza la palabra reservada para indicar donde se inicia una serie de instrucciones
    CREATE LOGIN ParcialFinal WITH PASSWORD = 'parcialfinalpoo'; --00043823 Si la condición anterior se cumple entonces creo el usuario llamado ParcialFinal y la contraseña
END --00043823 Se utiliza la palabra reservada para indicar donde se termina una serie de instrucciones
GO --00043823 Esto se usa para que se ejecute la query y luego siga con las demás querys

CREATE DATABASE BCN; --00043823 Creo la base de datos llamada BCN
GO --00043823 Esto se usa para que se ejecute la query y luego siga con las demás querys

USE BCN; --00043823 Me dirijo a la base de datos llamada BCN
GO --00043823 Esto se usa para que se ejecute la query y luego siga con las demás querys

-- Crear usuario en la base de datos
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'ParcialFinal') --00043823 Primero verifico si no existe el usuario llamado ParcialFinal en la base de datos BCN
BEGIN --00043823 Se utiliza la palabra reservada para indicar donde se inicia una serie de instrucciones
    CREATE USER ParcialFinal FOR LOGIN ParcialFinal; --00043823 Si la condición anterior se cumple entonces creo el usuario llamado ParcialFinal para la base de datos BCN
END --00043823 Se utiliza la palabra reservada para indicar donde se termina una serie de instrucciones
GO --00043823 Esto se usa para que se ejecute la query y luego siga con las demás querys

-- Asignar roles al usuario
EXEC sp_addrolemember 'db_datareader', 'ParcialFinal'; --00043823 Asigno el rol de lector al usuario ParcialFinal en la base de datos
EXEC sp_addrolemember 'db_datawriter', 'ParcialFinal'; --00043823 Asigno el rol de escritor al usuario ParcialFinal en la base de datos
GO --00043823 Esto se usa para que se ejecute la query y luego siga con las demás querys


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
