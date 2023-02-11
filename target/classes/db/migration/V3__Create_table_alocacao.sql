CREATE TABLE `alocacao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia` varchar(50) DEFAULT NULL,
  `tempo` varchar(50) DEFAULT NULL,
  `nomeProjeto` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;