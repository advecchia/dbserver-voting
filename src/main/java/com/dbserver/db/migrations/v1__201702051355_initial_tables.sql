CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` blob NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_users_UNIQUE` (`id`),
  UNIQUE KEY `username_users_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = 'Table that keep information about valid users.';

CREATE TABLE IF NOT EXISTS `restaurants` (
  `id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_restaurants_UNIQUE` (`id`),
  UNIQUE KEY `name_restaurants_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = 'Table that keep information about valid restaurants.';

CREATE TABLE IF NOT EXISTS `winners` (
  `id` varchar(36) NOT NULL,
  `restaurantId` varchar(36) NOT NULL,
  `lunchDate` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_winners_UNIQUE` (`id` ASC),
  INDEX `fk_winners_restaurantId_idx` (`restaurantId` ASC),
  CONSTRAINT `fk_winners_restaurantId` FOREIGN KEY (`restaurantId`) REFERENCES `restaurants` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = 'Table that keep information about existing restaurant winners.';

CREATE TABLE IF NOT EXISTS `votes` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `restaurantId` varchar(36) NOT NULL,
  `votingDate` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_votes_UNIQUE` (`id` ASC),
  INDEX `fk_votes_userId_idx` (`userId` ASC),
  INDEX `fk_votes_restaurantId_idx` (`restaurantId` ASC),
  CONSTRAINT `fk_votes_userId` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_votes_restaurantId` FOREIGN KEY (`restaurantId`) REFERENCES `restaurants` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = 'Table that log information about user voting on restaurants.';
