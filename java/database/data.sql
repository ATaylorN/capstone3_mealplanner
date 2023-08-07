BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO ingredients (ingredient_id, ingredient_name, ingredient_image) VALUES (1, 'banana', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'), (2, 'salmon', 'https://images.pexels.com/photos/3296279/pexels-photo-3296279.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'), (3, 'flour', 'https://images.pexels.com/photos/17426324/pexels-photo-17426324/free-photo-of-food-roller-on-flour-and-dough.jpeg?auto=compress&cs=tinysrgb&w=600');
INSERT INTO recipes(recipe_id, recipe_name, recipe_image, recipe_ingredients, instructions) VALUES (1, 'multiple bananas and salmons', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'ingredients', 'put some bananas and salmons on your plate, you got a feast its awesome'  );
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, notes) VALUES (1, 1, 2, 'this is bananas!'), (1, 2, 2, 'this is salmons!');


COMMIT TRANSACTION;
