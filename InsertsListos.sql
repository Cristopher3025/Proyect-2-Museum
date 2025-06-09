-- Elimina las filas anteriores para evitar duplicados (solo si estás reiniciando datos)
DELETE FROM EPOCAS;
COMMIT;

-- Inserta los datos nuevamente
INSERT INTO EPOCAS (IDEPOCA, NOMBRE, DESCRIPCION) VALUES (1, 'Prehistoria', 'Antes de la escritura');
INSERT INTO EPOCAS (IDEPOCA, NOMBRE, DESCRIPCION) VALUES (2, 'Edad Antigua', 'Primeras civilizaciones');
INSERT INTO EPOCAS (IDEPOCA, NOMBRE, DESCRIPCION) VALUES (3, 'Edad Media', 'Imperios y religiones');
INSERT INTO EPOCAS (IDEPOCA, NOMBRE, DESCRIPCION) VALUES (4, 'Edad Moderna', 'Renacimiento y descubrimientos');
INSERT INTO EPOCAS (IDEPOCA, NOMBRE, DESCRIPCION) VALUES (5, 'Edad Contemporánea', 'Revoluciones e industrialización');

-- Guarda los cambios en la base de datos
COMMIT;
