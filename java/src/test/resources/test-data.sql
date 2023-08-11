BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');

INSERT INTO ingredients (ingredient_name) VALUES ('Banana');
INSERT INTO ingredients (ingredient_name) VALUES ('Eggs');
INSERT INTO ingredients (ingredient_name) VALUES ('Yogurt');

INSERT INTO recipes (user_id, recipe_name, recipe_ingredients, instructions) VALUES (1, 'recipe1', 'a, b, c', 'abc instructions');
INSERT INTO recipes (user_id, recipe_name, recipe_ingredients, instructions) VALUES (1, 'recipe2', 'd,e,f', 'def instructions');
INSERT INTO recipes (user_id, recipe_name, recipe_ingredients, instructions) VALUES (2, 'recipe3', 'g, h, i', 'ghi instructions');

INSERT INTO meals(meal_name, user_id, meal_type) VALUES ('Southern dinner', 1, 'dinner');
INSERT INTO meals(meal_name, user_id, meal_type) VALUES ('Boss breakfast', 1, 'breakfast');
INSERT INTO meals(meal_name, user_id, meal_type) VALUES ('Power lunch', 2, 'lunch');
INSERT INTO meals(meal_name, user_id, meal_type) VALUES ('Big breakfast', 2, 'breakfast');

INSERT INTO meal_recipes(meal_id, recipe_id) VALUES (4, 2);

INSERT INTO meal_plans(user_id, meal_id, plan_date) VALUES (1, 4, '2023-10-29');
INSERT INTO meal_plans(user_id, meal_id, plan_date) VALUES (1, 3, '2024-12-13');



COMMIT TRANSACTION;
