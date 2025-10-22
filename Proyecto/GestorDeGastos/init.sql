-- Crear la base de datos si no existe (ya se crea por POSTGRES_DB)
-- Conectar a la base de datos gestor_gastos

-- Crear la tabla Pagos
CREATE TABLE IF NOT EXISTS Pagos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    cantidad DECIMAL(10, 2) NOT NULL,
    cuando TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_pagos_categoria ON Pagos(categoria);
CREATE INDEX idx_pagos_cuando ON Pagos(cuando);

-- Insertar algunos datos de ejemplo
INSERT INTO Pagos (nombre, categoria, cantidad, cuando) VALUES
    ('Compra supermercado', 'Alimentación', 45.50, '2025-10-15 10:30:00'),
    ('Gasolina', 'Transporte', 60.00, '2025-10-16 08:15:00'),
    ('Cena restaurante', 'Ocio', 35.75, '2025-10-18 20:45:00'),
    ('Factura luz', 'Servicios', 85.20, '2025-10-01 00:00:00'),
    ('Netflix', 'Entretenimiento', 12.99, '2025-10-05 00:00:00');

-- Comentarios en la tabla
COMMENT ON TABLE Pagos IS 'Tabla para el control de gastos personales';
COMMENT ON COLUMN Pagos.id IS 'Identificador único del pago';
COMMENT ON COLUMN Pagos.nombre IS 'Descripción del pago';
COMMENT ON COLUMN Pagos.categoria IS 'Categoría del gasto';
COMMENT ON COLUMN Pagos.cantidad IS 'Cantidad del pago en euros';
COMMENT ON COLUMN Pagos.cuando IS 'Fecha y hora del pago';
