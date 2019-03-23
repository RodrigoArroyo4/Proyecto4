
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

INSERT INTO faculty (faculty_id, faculty_name, office) 
VALUES
('001A', 'Rodrigo', 'N-201'), 
('002A', 'Fausto', 'Hayek-301'), 
('003A', 'Noel', 'Hayek-302');

INSERT INTO course (course_id, faculty_id, course) 
VALUES
('CMP-100', '001A', 'Programacion I'), 
('CMP-200', '002A', 'Programacion III'), 
('CMP-500', '003A', 'Base de Datos');

 

