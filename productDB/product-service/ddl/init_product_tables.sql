CREATE SCHEMA IF NOT EXISTS fitness;

CREATE TABLE IF NOT EXISTS fitness.product
(
    id UUID NOT NULL,
    dt_create TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    dt_update TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    title TEXT NOT NULL,
    weight INT NOT NULL,
    calories INT NOT NULL,
    proteins NUMERIC(10,2) NOT NULL,
    fats NUMERIC(10,2) NOT NULL,
    carbohydrates NUMERIC(10,2) NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (id),
    CONSTRAINT repeat_product UNIQUE(title, weight,calories, proteins,fats,carbohydrates)
);

CREATE TABLE IF NOT EXISTS fitness.product_model
(
    id uuid NOT NULL,
    product_id uuid NOT NULL,
    weight INT NOT NULL,
    CONSTRAINT product_model_pk PRIMARY KEY (id),
    CONSTRAINT product_id_fk FOREIGN KEY (product_id)
            REFERENCES fitness.product (id)
);

CREATE TABLE IF NOT EXISTS fitness.recipe
(
    id uuid NOT NULL,
    dt_create TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    dt_update TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    title TEXT NOT NULL,
    CONSTRAINT recipe_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS fitness.recipe_product (
    recipe_id UUID,
    product_model_id UUID,
    CONSTRAINT recipe_id FOREIGN KEY (recipe_id)
                REFERENCES fitness.recipe (id),
    CONSTRAINT product_model_id_pk FOREIGN KEY (product_model_id)
                REFERENCES fitness.product_model (id)
);