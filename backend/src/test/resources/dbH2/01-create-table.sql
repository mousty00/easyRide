CREATE TABLE users
(
    Id                BIGINT      NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name        VARCHAR(60) NOT NULL,
    last_name         VARCHAR(60) NOT NULL,
    birth_date        DATE        NOT NULL,
    tax_id_code       VARCHAR(16) NOT NULL UNIQUE,
    registration_date DATE        NOT NULL
);

CREATE TABLE drivers
(
    Id          BIGINT      NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name  VARCHAR(60) NOT NULL,
    last_name   VARCHAR(60) NOT NULL,
    birth_date  DATE        NOT NULL,
    tax_id_code VARCHAR(16) NOT NULL UNIQUE
);

CREATE TABLE vehicles
(
    id           BIGINT      NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    plate_number VARCHAR(7)  NOT NULL UNIQUE,
    model        VARCHAR(90) NOT NULL,
    color        VARCHAR(50) NOT NULL,
    id_driver    BIGINT,
    FOREIGN KEY (id_driver) REFERENCES drivers (id)
);

CREATE TABLE rides
(
    id         BIGINT      NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    state      VARCHAR(25) NOT NULL,
    distance   DOUBLE      NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NOT NULL,
    id_user    BIGINT      NOT NULL,
    price      DOUBLE      NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users (id),
    id_driver  BIGINT      NOT NULL,
    FOREIGN KEY (id_driver) REFERENCES drivers (id),
    id_vehicle BIGINT      NOT NULL,
    FOREIGN KEY (id_vehicle) REFERENCES vehicles (id)
);

CREATE VIEW ride_detail_view AS
SELECT
    r.id AS id_ride,
    r.state AS state,
    r.start_date AS start_date,
    r.end_date AS end_date,
    r.price AS price,
    CONCAT(u.first_name, ' ', u.last_name) AS "user",
    u.birth_date AS birth_date,
    CONCAT(d.first_name, ' ', d.last_name) AS driver,
    v.model AS vehicle,
    v.plate_number AS plate_number
FROM rides r
         JOIN users u ON u.id = r.id_user
         JOIN drivers d ON d.id = r.id_driver
         JOIN vehicles v ON v.id = r.id_vehicle;

