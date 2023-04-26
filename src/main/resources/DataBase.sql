DROP DATABASE despensa;

CREATE DATABASE despensa;

use despensa;

CREATE TABLE almacenamientos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(255),
  lugar VARCHAR(255)
);

CREATE TABLE productos (
  id BIGINT PRIMARY KEY,
  imagen TEXT NOT NULL,
  nombre VARCHAR(255)
);

CREATE TABLE inventario (
  id INT PRIMARY KEY AUTO_INCREMENT,
  almacenamiento_id BIGINT,
  producto_id BIGINT,
  cantidad INT,
  precio DECIMAL(10,2),
  fecha_caducidad DATE,
  FOREIGN KEY (almacenamiento_id) REFERENCES almacenamientos(id),
  FOREIGN KEY (producto_id) REFERENCES productos(id)
);

INSERT INTO almacenamientos (id, nombre, lugar) VALUES 
(1, 'Cajon derecho superior', 'Cocina'),
(2, 'Cajon central superior', 'Cocina'),
(3, 'Cajon izquierdo superior', 'Cocina'),
(4, 'Cajon izquierdo inferior', 'Cocina'),
(5, 'Cajon derecho inferior', 'Cocina'),
(6, 'Cajon central inferior', 'Cocina'),
(7, 'Cajon izquierdo inferior', 'Cocina'),
(8, 'Cajon al lado nevera superior', 'Cocina'),
(9, 'Cajon al lado nevera inferior', 'Cocina'),
(10, 'Nevera', 'Cocina'),
(11, 'Congelador', 'Cocina'),
(12, 'Cajon izquierdo superior', 'Sotano'),
(13, 'Cajon izquierdo superior', 'Sotano'),
(14, 'Conjelador', 'Sotano');

INSERT INTO productos (id, imagen, nombre) VALUES 
(8480000105745, 'https://prod-mercadona.imgix.net/images/3631d0a515de4e1ac9237295370bf394.jpg?fit=crop&h=600&w=600', 'Leche entera'),
(8480000105783, 'https://prod-mercadona.imgix.net/images/d1df90671044f2bfe464b666ef0cb142.jpg?fit=crop&h=600&w=600', 'Leche desnatada'),
(8480000278272, 'https://prod-mercadona.imgix.net/images/1c1432367a84ed84cf1ac788939b0aeb.jpg?fit=crop&h=600&w=600', 'Te'),
(8480000838674, 'https://prod-mercadona.imgix.net/images/f26045084b08d8792979df13779e97d3.jpg?fit=crop&h=600&w=600', 'Pan de molde blanco'),
(8480000114549, 'https://prod-mercadona.imgix.net/images/00111b225acf03c4f789eb4c4c790437.jpg?fit=crop&h=600&w=600', 'Te Miel'),
(8480000102263, 'https://prod-mercadona.imgix.net/images/d4fd064b82650b2384519c1a3683e19b.jpg?fit=crop&h=600&w=600', 'Zumo Fruta + leche Mediterráneo'),
(8480000614056, 'https://prod-mercadona.imgix.net/images/d87b46450e6a7263c554dc9e1f351479.jpg?fit=crop&h=600&w=600', 'Patatas corte grueso'),
(8480000614162, 'https://prod-mercadona.imgix.net/images/da149a1f0e06f867d27d50c1d2235a89.jpg?fit=crop&h=600&w=600', 'Patatas gajo'),
(8480000171726, 'https://prod-mercadona.imgix.net/images/fe381cd6de0f3d80e966ebfb7b2ea387.jpg?fit=crop&h=600&w=600', 'Allioli'),
(8480000633231, 'https://prod-mercadona.imgix.net/images/a83470c87a6d3342c96dd0a449237604.jpg?fit=crop&h=600&w=600', 'Nuggets de pollo'),
(8421610034537, 'https://prod-mercadona.imgix.net/images/668f83f0d9ad4607ef23885ee89f29a9.jpg?fit=crop&h=600&w=600', 'carne picada vacuno y cerdo'),
(2304425004963, 'https://prod-mercadona.imgix.net/images/b8fb073339862c68387ecec6548fdd9a.jpg?fit=crop&h=600&w=600', 'Carrillada de cerdo'),
(2313710004403, 'https://prod-mercadona.imgix.net/images/d7a334df26137f530d2a66205c39a80e.jpg?fit=crop&h=600&w=600', 'Codillo de cerdo asado'),
(8480000635211, 'https://prod-mercadona.imgix.net/images/3762d36eb9a6d02b92ab4a5ff377f364.jpg?fit=crop&h=600&w=600', 'calzone de pollo'),
(8480000290724, 'https://prod-mercadona.imgix.net/images/b787b19514ee347086bd5551f6828115.jpg?fit=crop&h=600&w=600', 'Refresco cola'),
(8480000198976, 'https://prod-mercadona.imgix.net/images/c0ae6317c8dc8e2951aebffa8af7d9c9.jpg?fit=crop&h=600&w=600', 'Azúcar blanco'),
(8480000652355, 'https://prod-mercadona.imgix.net/images/031ac7420207837f33de188f96f04482.jpg?fit=crop&h=600&w=600', 'Chicle menta'),
(1234567891234, 'https://prod-mercadona.imgix.net/images/e1578b840fb47bc0cf262a090b099615.jpg?fit=crop&h=600&w=600', 'Jamoncitos de pollo'),
(8436021411273, 'https://prod-mercadona.imgix.net/images/b9c00335e0f0310810f6c54bea7925ae.jpg?fit=crop&h=600&w=600', 'Burger de vacuno y cerdo'),
(8480000093776, 'https://prod-mercadona.imgix.net/images/2a600aec4162df85bbb9fa4e3f50400b.jpg?fit=crop&h=600&w=600', 'Cereales rellenos de chocolate'),
(8480000095084, 'https://prod-mercadona.imgix.net/images/c483a6bccdc07cfe57800ed6024c81c8.jpg?fit=crop&h=600&w=600', 'Cereales copos de trigo') ;

INSERT INTO inventario (almacenamiento_id, producto_id, cantidad, fecha_caducidad) VALUES
(1, 8480000105745, 10, '2023-05-31'),
(2, 8480000105783, 5, '2023-06-15'),
(3, 8480000278272, 3, '2022-12-31'),
(4, 8480000838674, 2, '2023-01-15'),
(5, 8480000114549, 7, '2023-08-31'),
(6, 8480000102263, 1, '2022-09-30'),
(7, 8480000614056, 8, '2023-11-30'),
(8, 8480000614162, 4, '2023-02-28'),
(9, 8480000171726, 3, '2022-11-30'),
(10, 8480000633231, 6, '2023-07-31'),
(11, 8421610034537, 2, '2023-03-31'),
(12, 2304425004963, 3, '2022-10-31'),
(13, 2313710004403, 1, '2022-08-31'),
(14, 8480000635211, 2, '2023-04-30'),
(14, 8480000290724, 10, '2023-09-30'),
(3, 8480000198976, 5, '2023-12-31'),
(5, 8480000278272, 2, '2023-04-30'),
(1, 8480000614056, 5, '2024-05-12'),
(2, 8480000114549, 7, '2023-10-31'),
(3, 8480000198976, 3, '2023-11-30'),
(4, 8480000105745, 9, '2022-12-31'),
(6, 8480000171726, 4, '2024-09-30'),
(7, 8480000635211, 1, '2023-05-15'),
(8, 8480000290724, 6, '2024-07-31'),
(9, 8480000102263, 2, '2023-09-30'),
(10, 8480000633231, 8, '2022-12-15');
