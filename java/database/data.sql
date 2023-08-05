BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO ingredients (ingredient_name, ingredient_image) VALUES ('banana', 'https://images.pexels.com/photos/1166648/pexels-photo-1166648.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'), ('salmon', 'https://images.pexels.com/photos/3296279/pexels-photo-3296279.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'), ('flour', 'https://images.pexels.com/photos/17426324/pexels-photo-17426324/free-photo-of-food-roller-on-flour-and-dough.jpeg?auto=compress&cs=tinysrgb&w=600');

COMMIT TRANSACTION;
