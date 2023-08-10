BEGIN TRANSACTION;

DROP TABLE IF EXISTS meal_plans;
DROP TABLE IF EXISTS meal_recipes;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users_recipes;
DROP TABLE IF EXISTS recipe_ingredients;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS recipes;


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

CREATE TABLE recipes (
	recipe_id SERIAL,
	user_id int NOT NULL,
	recipe_name varchar (75) NOT NULL UNIQUE,
	recipe_image varchar (200),
	recipe_ingredients varchar(1000),
	instructions varchar (5000) NOT NULL,
	CONSTRAINT PK_recipe PRIMARY KEY (recipe_id),
	CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE recipe_ingredients (
    recipe_id int NOT NULL,
    ingredient_id int NOT NULL,
    quantity varchar(30),
    notes varchar(100),
    -- TODO: Quantities can range in types. Weights, volumes, imperial units, metric units.
    -- We could varchar the quantities for now, but this would make any kind of inventory calculation difficult.
    -- To get around this problem, we can create a category table to represent different quantity types.
    -- Could do this now, or later. Might be best to just get recipes working, then come back to it.
    CONSTRAINT FK_recipe_ingredients_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    CONSTRAINT FK_recipe_ingredients_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id)
);

CREATE TABLE users_recipes (
	user_id int NOT NULL,
	recipe_id int NOT NULL,
CONSTRAINT FK_userIds FOREIGN KEY (user_id) REFERENCES users(user_id),
CONSTRAINT FK_recipeIds FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);

CREATE TABLE meals (
	meal_id SERIAL,
	user_id int NOT NULL,
	meal_type varchar (20),
	CONSTRAINT PK_recipe PRIMARY KEY (meal_id),
	CONSTRAINT FK_userIds FOREIGN KEY (user_id) REFERENCES users(user_id)
);

--JOIN TABLE FOR MEALS AND RECIPES
CREATE TABLE meal_recipes (
	meal_id int NOT NULL,
	recipe_id int NOT NULL,
	CONSTRAINT FK_mealIds FOREIGN KEY (meal_id) REFERENCES meals(meal_id),
	CONSTRAINT FK_recipeIds FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);
-- TODO: Create table for meal plans
CREATE TABLE meal_plans (
	meal_plan_id SERIAL,
	user_id int NOT NULL,
	meal_id int NOT NULL,
	date DATE NOT NULL,
	CONSTRAINT PK_meal_plans PRIMARY KEY (meal_plan_id),
	CONSTRAINT FK_userIds FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_mealIds FOREIGN KEY (meal_id) REFERENCES meals(meal_id)
);


COMMIT TRANSACTION;
