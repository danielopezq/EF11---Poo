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
