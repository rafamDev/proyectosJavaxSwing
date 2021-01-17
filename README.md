# proyectosJavaxSwing
Proyectos con javax.Swing

PROYECTO(1) inventarioSwing


A) ESTRUCTURA BASE DE DATOS (mysql 8.0.22):

DROP DATABASE IF EXISTS Liceo;
CREATE DATABASE Liceo;
USE Liceo;

CREATE TABLE Actual(
	Cod INT NOT NULL AUTO_INCREMENT,
	Des VARCHAR(100) NOT NULL,
	Tipo VARCHAR(100) NOT NULL,
	Marca VARCHAR(100) NOT NULL,
	Modelo VARCHAR(100) NOT NULL,
	NumSerie VARCHAR(100) NULL,
	Resp VARCHAR(100) NULL,
	Local VARCHAR(100) NULL,
	FecAlta TIMESTAMP NOT NULL,
	FecMod TIMESTAMP NULL,
	FecBaja TIMESTAMP NULL,
	Motivo VARCHAR(500) NULL,
	Obs VARCHAR(1000) NULL,
	PRIMARY KEY (Cod)
);

CREATE TABLE Historico(
	ID INT NOT NULL AUTO_INCREMENT,
	Cod INT NOT NULL,
	Des VARCHAR(100) NOT NULL,
	Tipo VARCHAR(100) NOT NULL,
	Marca VARCHAR(100) NOT NULL,
	Modelo VARCHAR(100) NOT NULL,
	NumSerie VARCHAR(100) NULL,
	Resp VARCHAR(100) NULL,
	Local VARCHAR(100) NULL,
	FecAlta TIMESTAMP NOT NULL,
	FecMod TIMESTAMP NULL,
	FecBaja TIMESTAMP NULL,
	Motivo VARCHAR(500) NULL,
	Obs VARCHAR(1000) NULL,
	PRIMARY KEY (ID)
);

DELIMITER $$
 CREATE TRIGGER historico_trigger_AU AFTER UPDATE ON Actual 
 FOR EACH ROW 
  BEGIN
    IF NEW.Motivo IS NOT NULL && NEW.FecBaja IS NOT NULL THEN
	INSERT INTO Historico(Cod,Des,Tipo,Marca,Modelo,NumSerie,Resp,Local,FecAlta,FecMod,FecBaja,Motivo,Obs)
        VALUES(NEW.Cod,NEW.Des,NEW.Tipo,NEW.Marca,NEW.Modelo,NEW.NumSerie,NEW.Resp,NEW.Local,NEW.FecAlta,NEW.FecMod,NEW.FecBaja,NEW.Motivo,NEW.Obs);
    END IF;
  END $$
DELIMITER ;



B) FUNCION DEL PROGRAMA:

Este programa maquetado en javax.Swing realiza una serie de acciones muy básicas,
simulando ser un programa de inventario.

Es un CRUD que se comunica con una base de datos mysql, el cual da de baja o de alta cualquier tipo de mercancia,
siempre y cuando cumpla con los requisitos previos. Al ejecutar el programa se visualiza un JDialog en el que se insertan usuario y contraseña
del gestor de la base de datos, con el objetivo de crear la conexión. Una vez hemos entrado, podemos realizar las acciones anteriormente citadas y
además también podemos visualizar mediante un JTable, los productos actuales, historicos etc.  

Para finalizar, se comprueba mediante JUnit4 la validez del objeto conexión, descartando así errores de persistencia.




