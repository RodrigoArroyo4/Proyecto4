DROP TABLE transacciones;
DROP TABLE cuentas;
DROP TABLE cliente;

CREATE TABLE cliente (
   cliente_id int NOT NULL,
   nombre_cliente varchar (40) NOT NULL,
   PRIMARY KEY (cliente_id)
);

CREATE TABLE cuentas (
   cuenta_id int NOT NULL,
   cliente_id int NOT NULL,
   saldo decimal (19,4) NOT NULL,
   PRIMARY KEY (cuenta_id),
   FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id) ON DELETE CASCADE
);

CREATE TABLE transacciones (
   transaccion_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   cuenta_id int NOT NULL,
   tipo varchar (100) NOT NULL,
   valor decimal (19,4) NOT NULL,
   fecha date NOT NULL,
   PRIMARY KEY (transaccion_id),
   FOREIGN KEY (cuenta_id) REFERENCES cuentas (cuenta_id) ON DELETE CASCADE
);

INSERT INTO cliente (cliente_id, nombre_cliente) 
VALUES
(1716181654, 'Rodrigo Arroyo'),
(1716181656, 'Fausto Pasmay Presidente'),
(1716181657, 'Fausto Pasmay Prefecto'),
(1716181655, 'Aaron Salazar');

INSERT INTO cuentas (cuenta_id, cliente_id, saldo) 
VALUES
(0001, 1716181654, 2000),
(0003, 1716181656, 2000000),
(0004, 1716181654, 10000),
(0002, 1716181655, 150);

INSERT INTO transacciones (cuenta_id, tipo, valor, fecha)
VALUES
(0001, 'Deposito', 10, '2019-03-23'),
(0002, 'Deposito', 100, '2019-03-22');

 

