DROP TABLE IF EXISTS Client;

DROP TABLE IF EXISTS Manager;

CREATE TABLE Client (
    id SERIAL PRIMARY KEY,
    name varchar(50)
);

CREATE TABLE Manager (
    no SERIAL PRIMARY KEY,
    label varchar(50)
);
