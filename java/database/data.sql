BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO ingredients (ingredient_name, ingredient_image) VALUES ('banana', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
('salmon', 'https://images.pexels.com/photos/3296279/pexels-photo-3296279.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
('flour', 'https://spoonacular.com/cdn/ingredients_250x250/flour.png'),
('hazelnuts', 'https://spoonacular.com/cdn/ingredients_250x250/hazelnuts.jpg'),
('butter', 'https://spoonacular.com/cdn/ingredients_250x250/butter-sliced.jpg'),
('powdered sugar','https://spoonacular.com/cdn/ingredients_250x250/powdered-sugar.jpg'),
('sugar','https://spoonacular.com/cdn/ingredients_250x250/sugar-in-bowl.png'),
('baking powder', 'https://spoonacular.com/cdn/ingredients_250x250/white-powder.jpg'),
('table salt','https://spoonacular.com/cdn/ingredients_250x250/salt.jpg'),
('egg', 'https://spoonacular.com/cdn/ingredients_250x250/egg.png'),
('milk','https://spoonacular.com/cdn/ingredients_250x250/milk.png');

INSERT INTO recipes(user_id, recipe_name, recipe_image, recipe_ingredients, instructions) VALUES (1, 'Salmon-Flavored Bananas', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'bananas, salmon', 'put some bananas and salmons on your plate, you got a feast its awesome');
INSERT INTO recipes(user_id, recipe_name, recipe_image, recipe_ingredients, instructions) VALUES (2, 'Too Many Bananas', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'bananas', 'just a ton of bananas on a plate, you do you, I guess');
INSERT INTO recipes (user_id, recipe_name, recipe_image, recipe_ingredients, instructions) VALUES (2, 'Hazelnut Bites', 'https://www.naschkatze.me/wp-content/uploads/2019/12/Schokolis-mit-Haselnuss-1-768x578.jpg', 'butter, powdered sugar, flour, hazelnuts', 'Lightly brown the butter in a pan, stir in the powdered sugar, then let it cool. Stir in the flour and chill the dough for several hours. Shape small portions of dough into balls. Press a hazelnut into the center of each dough ball and then shape the ball back into a round shape. Place the balls on a tray and bake at 350F for about 20 minutes. After cooling, sprinkle it with powdered sugar. Makes about 20.');
INSERT INTO recipes (user_id, recipe_name, recipe_image, recipe_ingredients, instructions) VALUES (2, 'Basic Muffins','https://images.pexels.com/photos/1657343/pexels-photo-1657343.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', 'flour, sugar, baking powder, salt, egg, butter, milk', 'Sift flour with sugar, baking powder, and salt into a bowl. Make a well in the center. In a seperate bowl, beat egg with butter and milk. Pour all at once into the flour well. Stir just to moisten ingredients, with about 12 to 15 full circular strokes that scrape the bottom of the bowl. Grease muffin cups. Bake in a hot oven(425”F) for about 25 minutes or until well browned. Makes 10 muffins ½ cup size.');

INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, notes) VALUES (1, 1, 2, 'this is bananas!'), (1, 2, 2, 'this is salmons!');
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, notes) VALUES (3, 3, 1, '150g'), (3,4,1,'about 20 or so'),(3,5,1,'100g'),(3,6,1,'35g');
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity, notes) VALUES (4,3,1,'2 cups'),(4,7,1,'3 tablespoons'),(4,8,1,'1 tablespoon'),(4,9,1,'1/2 teaspoon'),(4,10,1,'1 large egg'),(4,5,1,'1/4 cup'),(4,11,1,'1 cup');

INSERT INTO users_recipes (user_id, recipe_id) VALUES (1, 1);
INSERT INTO users_recipes (user_id, recipe_id) VALUES (1, 2);

COMMIT TRANSACTION;

--SELECT * FROM ingredients;
--SELECT * FROM recipe_ingredients;
--SELECT * FROM recipes;