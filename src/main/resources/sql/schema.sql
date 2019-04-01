DROP TABLE IF EXISTS distance;
CREATE TABLE distance (
  device_id INT,
  data_id INT,
  dist DECIMAL
);

CREATE INDEX idx ON distance (data_id);