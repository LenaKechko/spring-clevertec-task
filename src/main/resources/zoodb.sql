-- Database: zoodb

DROP TABLE IF EXISTS animals;

DROP DATABASE IF EXISTS zoodb;

CREATE DATABASE zoodb;

CREATE TABLE IF NOT EXISTS animals
(
    id uuid NOT NULL PRIMARY KEY,
    name character varying(30) NOT NULL,
    type_of_animal character varying(30),
    class_of_animal character varying(30),
    weight double precision NOT NULL,
    height double precision NOT NULL,
    speed double precision NOT NULL
);

INSERT INTO animals(id, name, type_of_animal, class_of_animal, weight, height, speed) VALUES (gen_random_uuid(), 'Слон', 'Хоботные', 'Млекопитающие', 6640, 6.8, 40);
INSERT INTO animals(id, name, type_of_animal, class_of_animal, weight, height, speed) VALUES (gen_random_uuid(), 'Бегемот', 'Парнокопытные', 'Млекопитающие', 3000, 1.5, 35);
INSERT INTO animals(id, name, type_of_animal, class_of_animal, weight, height, speed) VALUES (gen_random_uuid(), 'Лев', 'Кошачие', 'Млекопитающие', 200, 1.2, 80);
INSERT INTO animals(id, name, type_of_animal, class_of_animal, weight, height, speed) VALUES (gen_random_uuid(), 'Жираф', 'Парнокопытные', 'Млекопитающие', 1200, 6.1, 55);
INSERT INTO animals(id, name, type_of_animal, class_of_animal, weight, height, speed) VALUES (gen_random_uuid(), 'Носорог', 'Непарнокопытные', 'Млекопитающие', 2900, 2, 45);