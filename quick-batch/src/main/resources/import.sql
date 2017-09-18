DROP TABLE IF EXISTS reader;
DROP TABLE IF EXISTS writer;

CREATE TABLE `reader` (`id` INT  NOT NULL AUTO_INCREMENT,`firstName`  VARCHAR(20) NULL,`lastName`   VARCHAR(20) NULL,`random_num` VARCHAR(20) NULL, PRIMARY KEY (`id`));

CREATE TABLE `writer` (`id` INT  NOT NULL AUTO_INCREMENT,`full_name`  VARCHAR(40) NULL,`random_num` VARCHAR(20) NULL, PRIMARY KEY (`id`));

INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('abc', 'def', '1');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('def', 'zhu', '2');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('dummy', 'name', '3');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('non', 'pay', '4');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('spring', 'batch', '5');