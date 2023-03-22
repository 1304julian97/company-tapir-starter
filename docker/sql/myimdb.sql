-- Database
CREATE DATABASE myimdb;
\c myimdb;






-- companies
CREATE TABLE companies (
  nit character varying NOT NULL,
  PRIMARY KEY (nit),
  name character varying NOT NULL,
  year_of_creation smallint NOT NULL,
    ceo_id character varying NOT NULL

);



INSERT INTO companies (nit, name, year_of_creation, ceo_id) VALUES ('1036','47deg','2010','Julian C');
INSERT INTO companies (nit, name, year_of_creation, ceo_id) VALUES ('10367','50deg','2010','Julian C');
COMMIT;