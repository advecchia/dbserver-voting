/* Inserting some users */
SET @user1 := uuid();
SET @user2 := uuid();
SET @user3 := uuid();
SET @user4 := uuid();
SET @user5 := uuid();

INSERT INTO `users` (`id`,`name`)
VALUES
	(@user1, "Afonso"), (@user2, "Bárbara"), (@user3, "Pedro"), 
	(@user4, "Tatiana"), (@user5, "Wilson");

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
SET @initialDate := DATE('2017-01-16'); /* 16-20 january week*/

INSERT INTO `votes` (`userId`, `restaurantId`, `votingDate`)
VALUES
	(@user1, @restaurant5, @initialDate),
	(@user2, @restaurant1, @initialDate),
	(@user3, @restaurant5, @initialDate),
	(@user4, @restaurant2, @initialDate),
	(@user5, @restaurant3, @initialDate),

	(@user2, @restaurant3, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(@user3, @restaurant3, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(@user4, @restaurant1, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(@user5, @restaurant1, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(@user1, @restaurant1, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	
	(@user3, @restaurant2, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(@user4, @restaurant3, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(@user5, @restaurant3, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(@user1, @restaurant5, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(@user2, @restaurant3, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	
	(@user4, @restaurant5, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(@user5, @restaurant3, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(@user1, @restaurant2, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(@user2, @restaurant4, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(@user3, @restaurant4, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	
	(@user5, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY)),
	(@user1, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY)),
	(@user2, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY)),
	(@user3, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY)),
	(@user4, @restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY));


/* Inserting some winners */
INSERT INTO `winners` (`restaurantId`, `lunchDate`)
VALUES
	(@restaurant5, @initialDate),
	(@restaurant1, DATE_ADD(@initialDate, INTERVAL 1 DAY)),
	(@restaurant3, DATE_ADD(@initialDate, INTERVAL 2 DAY)),
	(@restaurant4, DATE_ADD(@initialDate, INTERVAL 3 DAY)),
	(@restaurant2, DATE_ADD(@initialDate, INTERVAL 4 DAY));