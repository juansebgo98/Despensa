DROP DATABASE despensa;

CREATE DATABASE despensa;

use despensa;

CREATE TABLE almacenamientos (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  lugar VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE productos (
  id BIGINT NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  cantidad INT NOT NULL,
  imagen TEXT NOT NULL,
  descripcion VARCHAR(255) NOT NULL,
  almacenamiento_id BIGINT DEFAULT NULL,
  CONSTRAINT sub_producto_almacenamiento_fk FOREIGN KEY (almacenamiento_id) REFERENCES almacenamientos (id),
  PRIMARY KEY (id)
);

CREATE TABLE sub_productos (
  id BIGINT NOT NULL AUTO_INCREMENT,
  fecha_caducidad DATE NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  producto_id BIGINT NOT NULL,
  almacenamiento_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT sub_producto_almacenamiento_fk FOREIGN KEY (almacenamiento_id) REFERENCES almacenamientos (id),
  CONSTRAINT sub_productos_producto_fk FOREIGN KEY (producto_id) REFERENCES productos (id)
);

-- Insertar algunos almacenamientos
INSERT INTO almacenamientos (id, nombre, lugar)
VALUES
    (1, 'Despensa', 'Cocina'),
    (2, 'Nevera', 'Cocina'),
    (3, 'Alacena', 'Cocina'),
    (4, 'Garaje', 'Sótano'),
    (5, 'Armario', 'Dormitorio'),
	(6, 'Cajón', 'Escritorio'),
	(7, 'Baúl', 'Sala'),
	(8, 'Repisa', 'Baño'),
	(9, 'Gaveta', 'Comedor'),
	(10, 'Estantería', 'Sótano'),
	(11, 'Caja', 'Armario'),
	(12, 'Mueble', 'Cocina'),
	(13, 'Maletero', 'Auto'),
	(14, 'Cesta', 'Lavandería'),
	(15, 'Estante', 'Garaje'),
	(16, 'Anaquel', 'Cocina');

-- Insertar algunos productos
INSERT INTO productos (id, nombre, cantidad, imagen, descripcion, almacenamiento_id)
VALUES
    (1, 'Leche', 1, 'https://imagen.com/leche.jpg', 'Leche entera de vaca', 2),
    (2, 'Pan', 4, 'https://imagen.com/pan.jpg', 'Pan de molde blanco', 1),
    (3, 'Queso', 1, 'https://imagen.com/queso.jpg', 'Queso de cabra', 2),
    (4, 'Yogur', 1, 'https://imagen.com/yogur.jpg', 'Yogur de fresa', 2),
    (5, 'Azúcar', 1, 'https://imagen.com/azucar.jpg', 'Azúcar blanco', 3),
    (6, 'Aceite', 1, 'https://imagen.com/aceite.jpg', 'Aceite de oliva virgen extra', 3),
    (7, 'Harina', 1, 'https://imagen.com/harina.jpg', 'Harina de trigo', 1),
    (8, 'Huevos', 1, 'https://imagen.com/huevos.jpg', 'Huevos frescos de gallina', 2);

-- Insertar algunos subproductos
INSERT INTO sub_productos (id, fecha_caducidad, precio, producto_id)
VALUES
    (1, '2023-06-01', 1.50, 1),
    (2, '2023-05-15', 0.80, 2),
    (4, '2023-07-22', 0.90, 2),
    (6, '2023-09-15', 0.75, 2),
    (8, '2023-11-22', 0.85, 2),
    (3, '2023-08-01', 3.00, 3),
    (5, '2023-10-01', 1.00, 4),
    (7, '2023-12-01', 2.50, 6);
