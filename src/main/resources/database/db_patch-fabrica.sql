/**
* USUARIO : usr_fabrica
**/
DROP USER IF EXISTS 'fabrica_db'@'localhost';
CREATE USER IF NOT EXISTS 'usr_fabrica'@'localhost' IDENTIFIED BY 'fabrica123';

/**
* ESQUEMA/BASE DE DATOS : fabrica_db
**/
CREATE DATABASE IF NOT EXISTS fabrica_db;
GRANT ALL PRIVILEGES ON fabrica_db.* TO 'usr_fabrica'@'localhost';
FLUSH PRIVILEGES;
USE fabrica_db;

/*
* Tabla: HABILIDAD
*/
CREATE TABLE IF NOT EXISTS HABILIDAD
(
    id_habilidad     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_habilidad VARCHAR(85) NOT NULL
);

/*
* Tabla: PERSONA
*/
CREATE TABLE IF NOT EXISTS PERSONA
(
    id_persona       INT AUTO_INCREMENT PRIMARY KEY,
    identificacion   VARCHAR(35) NOT NULL UNIQUE,
    nombre_completo  VARCHAR(85) NOT NULL,
    direccion        VARCHAR(55) NOT NULL,
    fecha_nacimiento DATE,
    nombre_contacto  VARCHAR(45),
    numero_telefono  VARCHAR(15),
    es_trabajador    TINYINT(1)  NOT NULL
);

/*
* Tabla: MAQUINA 
*/
CREATE TABLE IF NOT EXISTS MAQUINA
(
    id_maquina   INT AUTO_INCREMENT PRIMARY KEY,
    numero_serie VARCHAR(35) NOT NULL,
    marca        VARCHAR(45) NOT NULL,
    modelo       VARCHAR(15) NOT NULL,
    fecha_compra DATETIME    NOT NULL
);

/*
* Tabla: MATERIAL 
*/
CREATE TABLE IF NOT EXISTS MATERIAL
(
    id_material     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_material VARCHAR(45) NOT NULL
);

/*
* Tabla: TIPO_PRODUCTO 
*/
CREATE TABLE IF NOT EXISTS TIPO_PRODUCTO
(
    id_tipo_producto     INT AUTO_INCREMENT PRIMARY KEY,
    id_material          INT         NOT NULL,
    nombre_tipo_producto VARCHAR(45) NOT NULL,
    FOREIGN KEY (id_material) REFERENCES MATERIAL (id_material)
);

/*
* Tabla: PRODUCTO
*/
CREATE TABLE IF NOT EXISTS PRODUCTO
(
    id_producto          INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo_producto     INT         NOT NULL,
    id_maquina           INT         NOT NULL,
    nombre_producto      VARCHAR(85) NOT NULL,
    descripcion_producto VARCHAR(85) NOT NULL,
    FOREIGN KEY (id_tipo_producto) REFERENCES TIPO_PRODUCTO (id_tipo_producto),
    FOREIGN KEY (id_maquina) REFERENCES MAQUINA (id_maquina)

);

/*
* Tabla: ORDEN 
*/
CREATE TABLE IF NOT EXISTS ORDEN
(
    id_orden                INT AUTO_INCREMENT PRIMARY KEY,
    id_persona              INT  NOT NULL,
    fecha_orden             DATE NOT NULL,
    fecha_prometida_entrega DATE,
    fecha_entrega           DATE,
    FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona)
);

/*
* Tabla: DETALLE_ORDEN 
*/
CREATE TABLE IF NOT EXISTS DETALLE_ORDEN
(
    id_detalle_orden INT AUTO_INCREMENT PRIMARY KEY,
    id_orden         INT            NOT NULL,
    id_producto      INT            NOT NULL,
    cantidad         INT            NOT NULL,
    precio_unitario  DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_orden) REFERENCES ORDEN (id_orden),
    FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto)
);

/*
* Tabla: HABILIDAD_TRABAJADOR 
*/
CREATE TABLE IF NOT EXISTS HABILIDAD_TRABAJADOR
(
    id_habilidad_trabajador INT AUTO_INCREMENT PRIMARY KEY,
    id_habilidad            INT      NOT NULL,
    id_persona              INT      NOT NULL,
    fecha_registro          DATETIME NOT NULL,
    FOREIGN KEY (id_habilidad) REFERENCES HABILIDAD (id_habilidad),
    FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona)
);

/*
* Tabla: DETALLE_OPERACION 
*/
CREATE TABLE IF NOT EXISTS DETALLE_OPERACION
(
    id_detalle_operacion INT AUTO_INCREMENT PRIMARY KEY,
    id_maquina           INT      NOT NULL,
    id_persona           INT      NOT NULL,
    fecha_registro       DATETIME NOT NULL,
    FOREIGN KEY (id_maquina) REFERENCES MAQUINA (id_maquina),
    FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona)
);