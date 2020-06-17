DROP TABLE IF EXISTS airline_history;
DROP TABLE IF EXISTS TicketNumber;
DROP TABLE IF EXISTS Ticket;
DROP TABLE IF EXISTS Reservation_Flight;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Flight;
DROP TABLE IF EXISTS Airline;
DROP TABLE IF EXISTS Airport;
DROP TABLE IF EXISTS Addres;

create table  IF NOT EXISTS Address
(
    id     bigint auto_increment
        primary key,
    city   varchar(255) null,
    state  varchar(255) null,
    street varchar(255) null,
    zip    varchar(255) null
);

create table IF NOT EXISTS  Airline
(
    id   bigint auto_increment
        primary key,
    code varchar(255) null,
    name varchar(255) null
);

create table  IF NOT EXISTS Airport
(
    id         bigint auto_increment
        primary key,
    code       varchar(3)   not null,
    name       varchar(255) null,
    address_id bigint       null,
    constraint FKh3npwca5jl7qf0r520ra7265q
        foreign key (address_id) references Address (id)
);

create table  IF NOT EXISTS Flight
(
    id            bigint auto_increment
        primary key,
    arrivalTime   datetime(6)  null,
    capacity      int          null,
    departureTime datetime(6)  null,
    number        varchar(255) null,
    airline_id    bigint       null,
    arrival_id    bigint       null,
    departure_id  bigint       null,
    constraint FK2cx6y1u9teulhffbdei5ahlhk
        foreign key (departure_id) references Airport (id),
    constraint FKd1oibum1k1gqypdpeioi5he3q
        foreign key (arrival_id) references Airport (id),
    constraint FKiovu1yeejovoyfdigekqm2poq
        foreign key (airline_id) references Airline (id)
);

create table  IF NOT EXISTS Reservation
(
    id                bigint auto_increment
        primary key,
    createdById       varchar(255) null,
    passengerId       varchar(255) null,
    reminded          bit          not null,
    reservationCode   varchar(6)   null,
    reservationStatus varchar(255) null);

create table  IF NOT EXISTS Reservation_Flight
(
    flight_id      bigint not null,
    reservation_id bigint not null,
    constraint FKdc9jmtef37o3ybp4rjqae4puw
        foreign key (reservation_id) references Flight (id),
    constraint FKrtxonhy4fj8oee90h1fbonf5d
        foreign key (flight_id) references Reservation (id)
);

create table  IF NOT EXISTS Ticket
(
    id             bigint auto_increment
        primary key,
    flightDate     date         null,
    number         varchar(255) null,
    passengerId    varchar(255) null,
    flight_id      bigint       null,
    reservation_id bigint       null,
    constraint FK3gxdfc9bp0kphuu661uwdwffp
        foreign key (flight_id) references Flight (id),
    constraint FKj8l7kg6ttwq0nxuguo1voef10
        foreign key (reservation_id) references Reservation (id)
);

create table  IF NOT EXISTS TicketNumber
(
    id   int          not null
        primary key,
    code varchar(255) null
);

create table  IF NOT EXISTS airline_history
(
    history varchar(2000) null,
    id      bigint        not null
        primary key,
    constraint FKoeedykl02uj2ophpxd4rcakxo
        foreign key (id) references Airline (id)
);

