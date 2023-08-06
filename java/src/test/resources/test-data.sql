BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');

INSERT INTO ingredients (ingredient_name) VALUES ('Banana');
INSERT INTO ingredients (ingredient_name) VALUES ('Eggs');
INSERT INTO ingredients (ingredient_name) VALUES ('Yogurt');

INSERT INTO recipes (recipe_name, recipe_ingredients, instructions) VALUES ('recipe1', 'a, b, c', 'abc instructions');
INSERT INTO recipes (recipe_name, recipe_ingredients, instructions) VALUES ('recipe2', 'd,e,f', 'def instructions');
INSERT INTO recipes (recipe_name, recipe_ingredients, instructions) VALUES ('recipe3', 'g, h, i', 'ghi instructions');

COMMIT TRANSACTION;
