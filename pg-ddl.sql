DROP SCHEMA PUBLIC CASCADE;
CREATE SCHEMA PUBLIC;

CREATE TABLE todo (
  id SERIAL PRIMARY KEY,
  task VARCHAR(128) NOT NULL
);