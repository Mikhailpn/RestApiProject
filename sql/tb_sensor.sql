create table sensor.Sensor
(id int generated by default as identity primary key,
 name varchar not null unique);