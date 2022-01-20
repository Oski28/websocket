/* DELETE */

DELETE FROM refresh_token; ALTER TABLE  refresh_token AUTO_INCREMENT = 1;
DELETE FROM user_role; ALTER TABLE user_role AUTO_INCREMENT = 1;
DELETE FROM message; ALTER TABLE message AUTO_INCREMENT = 1;
DELETE FROM user; ALTER TABLE user AUTO_INCREMENT = 1;
DELETE FROM role; ALTER TABLE role AUTO_INCREMENT = 1;

/* Roles */
INSERT INTO role(role) VALUE ("ROLE_USER"),("ROLE_MODERATOR"),("ROLE_ADMIN");

/* Users */
INSERT INTO user(password,username) VALUE
("$2a$10$T3kTt667dJDWjGvYE.KTlOfrhaTXHZApORlbGY1YwHb35DTZF7xOS","User"),/*H: User123*/
("$2a$10$hruixh2Ca.gHi7zSGlrIn.c3aEds6Rn5uAgys9impBh2wq0gNZZoy","Mod"), /*H: Mod123*/
("$2a$10$tlXWEsp0yBA6MBAd4v0kOO96t3tkOhVuj.0JgyCsqOzv8lyq/Jsae","Admin");/*H: Admin123*/

/* Message */
INSERT INTO message(content,sender_id) VALUE
("Cześć, kto ma piłkę?","1"),("Ja, tylko potrzeba pompkę :/?","1"),
("Ja wezmę pompkę :)","1"),("Git! :D","1");

/* User role */
INSERT INTO user_role(id_user,id_role) VALUE ("1","1"),("2","1"),("2","2"),("3","1"),("3","2"),("3","3");
