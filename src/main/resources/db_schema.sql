DROP TABLE IF EXISTS contact;

CREATE TABLE contact  (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    email VARCHAR(100),
    nom VARCHAR(40),
    prenom VARCHAR(40),
    societe VARCHAR(50),
    tel VARCHAR(20)
);