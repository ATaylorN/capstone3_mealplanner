BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS ingredients;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE ingredients (
	ingredient_id SERIAL,
	ingredient_name varchar(50) NOT NULL UNIQUE,
	ingredient_image varchar(200),
	CONSTRAINT PK_ingredient PRIMARY KEY (ingredient_id)
);

-- TODO: Create table for recipes

CREATE TABLE recipes (
	recipe_id SERIAL,
	recipe_name varchar (75) NOT NULL UNIQUE,
	recipe_image varchar (200),
	recipe_ingredients varchar (1000) NOT NULL,
	instructions varchar (5000) NOT NULL,
	CONSTRAINT PK_recipe PRIMARY KEY (recipe_id)
);

-- TODO: Create table for meals

-- TODO: Create table for meal plans

-- TODO: Create table for user ingredient inventory


COMMIT TRANSACTION;
