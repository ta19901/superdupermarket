CREATE TABLE IF NOT EXISTS products (
  id            SERIAL PRIMARY KEY,
  name          BPCHAR,
  type          BPCHAR,
  expiry_date   INTEGER,
  quality       INTEGER,
  base_price    REAL
);