DROP DATABASE IF EXISTS jschedule;
CREATE DATABASE IF NOT EXISTS jschedule;
COMMIT;
USE jschedule;

CREATE TABLE IF NOT EXISTS `jschedule`.`shift` (
  `id` INT NOT NULL,
  `code` VARCHAR(100) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `duration` VARCHAR(20) NULL DEFAULT NULL,
  `colorCode` CHAR(7) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `jschedule`.`shift_group` (
  `id` INT NOT NULL,
  `code` VARCHAR(100) NOT NULL,
  `name` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `jschedule`.`shift_grouping` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `day` INT NOT NULL,
  `shift_group_code` VARCHAR(100) NOT NULL,
  `shift_code` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_SGP_SG_idx` (`shift_group_code` ASC) VISIBLE,
  INDEX `FK_SGP_S` (`shift_code` ASC) VISIBLE,
  CONSTRAINT `FK_SGP_S`
    FOREIGN KEY (`shift_code`)
    REFERENCES `jschedule`.`shift` (`code`),
  CONSTRAINT `FK_SGP_SG`
    FOREIGN KEY (`shift_group_code`)
    REFERENCES `jschedule`.`shift_group` (`code`))
ENGINE = InnoDB;