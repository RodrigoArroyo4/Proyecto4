DROP TABLE faculty;
DROP TABLE course;

CREATE TABLE faculty (
   faculty_id varchar (20) NOT NULL,
   faculty_name varchar (20) NOT NULL,
   office varchar (30) NOT NULL,
   PRIMARY KEY (faculty_id)
);

CREATE TABLE course (
   course_id varchar (20) NOT NULL,
   faculty_id varchar (20) NOT NULL,
   course varchar (100) NOT NULL,
   PRIMARY KEY (course_id),
   FOREIGN KEY (faculty_id) REFERENCES faculty (faculty_id) ON DELETE CASCADE
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

 

