DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS branches;
DROP TABLE IF EXISTS franchises;


CREATE TABLE franchises (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE branches (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    franchise_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_franchise FOREIGN KEY (franchise_id) REFERENCES franchises(id) ON DELETE CASCADE
);

CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    branch_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0),
    CONSTRAINT fk_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE CASCADE,
    CONSTRAINT unique_product_name_per_branch UNIQUE (branch_id, name)
);

-- INSERTS
--1)
INSERT INTO franchises (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Accenture Retail');

INSERT INTO branches (id, franchise_id, name) VALUES
                                                  ('b1111111-1111-1111-1111-111111111111', '550e8400-e29b-41d4-a716-446655440000', 'Sucursal Medellin Centro'),
                                                  ('b2222222-2222-2222-2222-222222222222', '550e8400-e29b-41d4-a716-446655440000', 'Sucursal Bogota Norte');

INSERT INTO products (branch_id, name, stock) VALUES
                                                  ('b1111111-1111-1111-1111-111111111111', 'Zapatos Cuero', 450),
                                                  ('b1111111-1111-1111-1111-111111111111', 'Zapatos Running', 600),
                                                  ('b1111111-1111-1111-1111-111111111111', 'Medias Algodon', 200),
                                                  ('b2222222-2222-2222-2222-222222222222', 'Camiseta Polo', 350),
                                                  ('b2222222-2222-2222-2222-222222222222', 'Jean Clasico', 800);

--2)
INSERT INTO franchises (id, name) VALUES ('f001f001-f001-f001-f001-f001f001f001', 'Tech World');

INSERT INTO branches (id, franchise_id, name) VALUES
                                                  ('b3333333-3333-3333-3333-333333333333', 'f001f001-f001-f001-f001-f001f001f001', 'Tech Cali'),
                                                  ('b4444444-4444-4444-4444-444444444444', 'f001f001-f001-f001-f001-f001f001f001', 'Tech Bucaramanga');

INSERT INTO products (branch_id, name, stock) VALUES
                                                  ('b3333333-3333-3333-3333-333333333333', 'Mouse Optico', 1000),
                                                  ('b3333333-3333-3333-3333-333333333333', 'Teclado Gamer', 500),
                                                  ('b4444444-4444-4444-4444-444444444444', 'Monitor 24"', 150),
                                                  ('b4444444-4444-4444-4444-444444444444', 'Cable HDMI', 1500);

--3)
INSERT INTO franchises (id, name) VALUES ('f002f002-f002-f002-f002-f002f002f002', 'Foodies Group');

INSERT INTO branches (id, franchise_id, name) VALUES
                                                  ('b5555555-5555-5555-5555-555555555555', 'f002f002-f002-f002-f002-f002f002f002', 'Foodies Pereira'),
                                                  ('b6666666-6666-6666-6666-666666666666', 'f002f002-f002-f002-f002-f002f002f002', 'Foodies Cartagena');

INSERT INTO products (branch_id, name, stock) VALUES
                                                  ('b5555555-5555-5555-5555-555555555555', 'Salsa Tomate', 300),
                                                  ('b5555555-5555-5555-5555-555555555555', 'Pasta Italiana', 300),
                                                  ('b6666666-6666-6666-6666-666666666666', 'Aceite Oliva', 120),
                                                  ('b6666666-6666-6666-6666-666666666666', 'Sal Marina', 50);

-- REPORT QUERY
/*SELECT
    f.name AS franchise_name,
    b.name AS branch_name,
    p.name AS product_name,
    p.stock
FROM (
         SELECT
             *,
             ROW_NUMBER() OVER (PARTITION BY branch_id ORDER BY stock DESC) as rn
         FROM products
     ) p
         JOIN branches b ON p.branch_id = b.id
         JOIN franchises f ON b.franchise_id = f.id
WHERE p.rn = 1
  AND f.id = :franchiseId;*/