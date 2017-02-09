CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = 'Table that keep information about valid users.';

CREATE TABLE IF NOT EXISTS `restaurants` (
  `id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = 'Table that keep information about valid restaurants.';

CREATE TABLE IF NOT EXISTS `winners` (
  `restaurantId` varchar(36) NOT NULL,
  `lunchDate` date NOT NULL,
  PRIMARY KEY (`restaurantId`),
  CONSTRAINT `fk_winners_restaurantId` FOREIGN KEY (`restaurantId`) REFERENCES `restaurants` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = 'Table that keep information about existing restaurant winners.';

CREATE TABLE IF NOT EXISTS `votes` (
  `userId` varchar(36) NOT NULL,
  `restaurantId` varchar(36) NOT NULL,
  `votingDate` date NOT NULL,
  PRIMARY KEY (`userId`,`restaurantId`,`votingDate`),
  KEY `fk_urn_restaurantId_idx` (`restaurantId`),
  CONSTRAINT `fk_urn_restaurantId` FOREIGN KEY (`restaurantId`) REFERENCES `restaurants` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_urn_userId` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = 'Table that log information about user voting on restaurants.';