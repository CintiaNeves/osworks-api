CREATE TABLE CLIENTE(
	ID int IDENTITY(1,1) NOT NULL,
	NOME varchar(50) NOT NULL,
	TELEFONE varchar(11) NULL,
	EMAIL varchar(50) NULL,
 CONSTRAINT PK_CLIENTE PRIMARY KEY (ID));