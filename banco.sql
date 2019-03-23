
DROP TABLE cuentas;
DROP TABLE cliente;
DROP TABLE transacciones;

CREATE TABLE cliente (
   cliente_id integer (20) NOT NULL,
   nombre_cliente varchar (20) NOT NULL,
   PRIMARY KEY (cliente_id)
);

CREATE TABLE cuentas (
   cuenta_id integer NOT NULL,
   cliente_id integer NOT NULL,
   saldo money NOT NULL,
   PRIMARY KEY (cuenta_id)
   FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id) ON DELETE CASCADE
);

CREATE TABLE transacciones (
   transaccion_id integer NOT NULL,
   cuenta_id integer NOT NULL,
   tipo varchar (100) NOT NULL,
   valor money NOT NULL,
   fecha date NOT NULL,
   PRIMARY KEY (transaccion_id),
   FOREIGN KEY (cuenta_id) REFERENCES cuentas (cuenta_id) ON DELETE CASCADE
);

INSERT INTO cliente (cliente_id, nombre_cliente) 
VALUES
('1716181654', 'Rodrigo Arroyo'), 
('1716181655', 'Aaron Salazar');

INSERT INTO cuentas (cuenta_id, cliente_id, saldo) 
VALUES
('0001', '1716181654', '2000'), 
('0002', '1716181655', '150');

INSERT INTO transacciones (transaccion_id, cuenta_id, tipo, valor, fecha) 
VALUES
('0001', '0001', 'Deposito', 10, 2019-03-23), 
('0002', '0002', 'Deposito', 100, 2019-03-22);

 

