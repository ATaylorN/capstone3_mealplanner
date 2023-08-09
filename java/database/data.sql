BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO ingredients (ingredient_name, ingredient_image) VALUES ('banana', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
('salmon', 'https://images.pexels.com/photos/3296279/pexels-photo-3296279.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'), 
('flour', 'https://images.pexels.com/photos/17426324/pexels-photo-17426324/free-photo-of-food-roller-on-flour-and-dough.jpeg?auto=compress&cs=tinysrgb&w=600');

INSERT INTO recipes(recipe_id, user_id, recipe_name, recipe_image, recipe_ingredients, instructions) VALUES (1, 1, 'Salmon-Flavored Bananas', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'bananas, salmon', 'put some bananas and salmons on your plate, you got a feast its awesome');
INSERT INTO recipes(recipe_id, user_id, recipe_name, recipe_image, recipe_ingredients, instructions) VALUES (2, 2, 'Too Many Bananas', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'bananas', 'just a ton of bananas on a plate, you do you, I guess');
INSERT INTO recipes(recipe_id, user_id, recipe_name, recipe_image, recipe_ingredients, instructions) VALUES (3, 2, 'Salmon in Flour', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'salmon, flour', 'maybe try frying it?');

INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, notes) VALUES (1, 1, 2, 'this is bananas!'), (1, 2, 2, 'this is salmons!');
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, notes) VALUES (2, 1, 2, 'this is bananas!');
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, notes) VALUES (3, 2, 2, 'this is salmon!'), (1, 3, 2, 'this is flour!');

INSERT INTO users_recipes (user_id, recipe_id) VALUES (1, 1);
INSERT INTO users_recipes (user_id, recipe_id) VALUES (1, 2);
INSERT INTO users_recipes (user_id, recipe_id) VALUES (1, 3);
INSERT INTO users_recipes (user_id, recipe_id) VALUES (2, 3);

COMMIT TRANSACTION;
