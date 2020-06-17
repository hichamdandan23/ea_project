INSERT INTO Airline (code, name)
VALUES ('ABC', 'TEST AIRLINE ONE');

INSERT INTO Address (street ,city, state, zip)
VALUES ('test_street1', 'test_city1', 'test_state1', 'test_zip1');

INSERT INTO Address (street ,city, state, zip)
VALUES ('test_street2', 'test_city2', 'test_state2', 'test_zip2');

INSERT INTO Airport (code, name, address_id)
VALUES ('JFK', 'John F Kennedy Intl Airport', 1);

INSERT INTO Airport (code, name, address_id)
VALUES ('NYK', 'New York Intl Airport', 2);

INSERT INTO Flight (airline_id, arrival_id, departure_id, arrivalTime, departureTime, capacity, number)
VALUES (1, 2, 1, '2020-06-16 13:00:00', '2020-06-15 13:00:00', 1, '1122');

INSERT INTO Flight (airline_id, arrival_id, departure_id, arrivalTime, departureTime, capacity, number)
VALUES (1, 1, 2, '2020-06-18 13:00:00', '2020-06-17 13:00:00', 100, '1123');
