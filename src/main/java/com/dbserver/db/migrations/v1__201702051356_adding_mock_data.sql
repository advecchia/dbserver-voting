/* Inserting some users */
SET @salt := "-my-salt-string-";
SET @user1 := uuid();
SET @user2 := uuid();
SET @user3 := uuid();
SET @user4 := uuid();
SET @user5 := uuid();

INSERT INTO `users` (`id`,`name`,`username`,`password`)
VALUES
	(@user1, "Afonso", "afonso", to_base64(aes_encrypt(@user1, sha2(@salt,256)))), 
	(@user2, "Bárbara", "barbara", to_base64(aes_encrypt(@user2, sha2(@salt,256)))), 
	(@user3, "Pedro", "pedro", to_base64(aes_encrypt(@user3, sha2(@salt,256)))), 
	(@user4, "Tatiana", "tatiana", to_base64(aes_encrypt(@user4, sha2(@salt,256)))), 
	(@user5, "Wilson", "wilson", to_base64(aes_encrypt(@user5, sha2(@salt,256))));

/* Inserting some restaurants */
SET @restaurant1 := uuid();
SET @restaurant2 := uuid();
SET @restaurant3 := uuid();
SET @restaurant4 := uuid();
SET @restaurant5 := uuid();

INSERT INTO `restaurants` (`id`, `name`)
VALUES
	(@restaurant1, "Restaurante Sabor Família"), (@restaurant2, "Bar Maza"), (@restaurant3, "Restaurante Palatus"), 
	(@restaurant4, "Panorama Gastronômico"), (@restaurant5, "Vila Olímpica Restaurante");

/* Inserting some votes */
SET @initialDate := DATE("2017-01-16"); /* 16-20 january week*/

INSERT INTO `votes` (`id`, `userId`, `restaurantId`, `votingDate`)
VALUES
	(uuid(), @user1, @restaurant5, @initialDate),
	(uuid(), @user2, @restaurant1, @initialDate),
	(uuid(), @user3, @restaurant5, @initialDate),
	(uuid(), @user4, @restaurant2, @initialDate),
	(uuid(), @user5, @restaurant3, @initialDate),

	(uuid(), @user2, @restaurant3, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(uuid(), @user3, @restaurant3, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(uuid(), @user4, @restaurant1, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(uuid(), @user5, @restaurant1, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(uuid(), @user1, @restaurant1, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	
	(uuid(), @user3, @restaurant2, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(uuid(), @user4, @restaurant3, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(uuid(), @user5, @restaurant3, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(uuid(), @user1, @restaurant5, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(uuid(), @user2, @restaurant3, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	
	(uuid(), @user4, @restaurant5, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(uuid(), @user5, @restaurant3, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(uuid(), @user1, @restaurant2, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(uuid(), @user2, @restaurant4, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(uuid(), @user3, @restaurant4, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	
	(uuid(), @user5, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY)),
	(uuid(), @user1, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY)),
	(uuid(), @user2, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY)),
	(uuid(), @user3, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY)),
	(uuid(), @user4, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY));


/* Inserting some winners */
INSERT INTO `winners` (`id`, `restaurantId`, `lunchDate`)
VALUES
	(uuid(), @restaurant5, @initialDate),
	(uuid(), @restaurant1, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(uuid(), @restaurant3, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(uuid(), @restaurant4, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(uuid(), @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY));