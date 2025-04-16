INSERT INTO users (first_name, last_name, birth_date, tax_id_code, registration_date)
VALUES ('Moustapha', 'Ndiaye', '1985-06-15', 'RSSMRC85H15A0', '2023-01-15'),
       ('Luca', 'Bianchi', '1990-04-20', 'BNCLCU90D20A1', '2023-02-10');



INSERT INTO drivers (first_name, last_name, birth_date, tax_id_code)
VALUES ('Alessandro', 'Conti', '1980-01-15', 'CNTALS80A15A0'),
       ('Carla', 'Marini', '1987-03-22', 'MRNCRL87C62A1');



INSERT INTO vehicles (plate_number, model, color, id_driver)
VALUES ('AB901LM', 'Tesla CyberTruck', 'Silver', 1),
       ('XY432JK', 'Volkswagen Tiguan', 'Yellow', 2);



INSERT INTO rides (state, distance, start_date, end_date, id_user, price, id_driver, id_vehicle)
VALUES
    ('completed', 356.45, '2023-01-15', '2023-01-16', 1, 45.75, 1, 1),
    ('cancelled', 520.32, '2023-02-10', '2023-02-11', 2, 30.20, 2, 2);

