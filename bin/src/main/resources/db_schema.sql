DROP TABLE IF EXISTS contact;

CREATE TABLE contact  (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    prenom VARCHAR(40),
    nom VARCHAR(40),
    email VARCHAR(100),
    societe VARCHAR(50),
    tel VARCHAR(20)
);