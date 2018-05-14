DROP TABLE IF EXISTS contact;

CREATE TABLE contact  (
    
    email VARCHAR(100) PRIMARY KEY ,
    nom VARCHAR(40),
    prenom VARCHAR(40),
    societe VARCHAR(50),
    tel VARCHAR(20)
);