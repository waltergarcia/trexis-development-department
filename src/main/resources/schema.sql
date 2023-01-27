DROP TABLE IF EXISTS `cost`;

CREATE TABLE IF NOT EXISTS `cost` (
  `id` BIGINT NOT NULL AUTO_INCREMENT  PRIMARY KEY,
  `cost` TINYINT NOT NULL
);

----------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `level`;

CREATE TABLE IF NOT EXISTS `level` (
  `id` BIGINT NOT NULL AUTO_INCREMENT  PRIMARY KEY,
  `level` varchar NOT NULL
);

----------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE IF NOT EXISTS `role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT  PRIMARY KEY,
  `role` varchar NOT NULL
);

----------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `employee`;

CREATE TABLE IF NOT EXISTS `employee` (
  `id` BIGINT NOT NULL AUTO_INCREMENT  PRIMARY KEY,
  `name` varchar NOT NULL,
  `email` varchar NOT NULL,
  `id_role` BIGINT NOT NULL,
  `id_cost` BIGINT NOT NULL,
  `id_level` BIGINT,
  `id_manager` BIGINT,
  FOREIGN KEY (id_role) REFERENCES role(id),
  FOREIGN KEY (id_cost) REFERENCES cost(id),
  FOREIGN KEY (id_level) REFERENCES level(id)
);