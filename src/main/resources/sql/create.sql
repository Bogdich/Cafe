-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema cafe
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cafe` ;

-- -----------------------------------------------------
-- Schema cafe
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cafe` DEFAULT CHARACTER SET utf8 ;
USE `cafe` ;

-- -----------------------------------------------------
-- Table `cafe`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`role` ;

CREATE TABLE IF NOT EXISTS `cafe`.`role` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cafe`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`user` ;

CREATE TABLE IF NOT EXISTS `cafe`.`user` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(150) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `account_balance` DECIMAL(5,2) NOT NULL DEFAULT 0,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `role_id` TINYINT UNSIGNED NOT NULL DEFAULT 2,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role_idx` (`role_id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  CONSTRAINT `fk_user_role`
  FOREIGN KEY (`role_id`)
  REFERENCES `cafe`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `cafe`.`user_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`user_info` ;

CREATE TABLE IF NOT EXISTS `cafe`.`user_info` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` INT(9) UNSIGNED ZEROFILL NOT NULL,
  `street` VARCHAR(150) NOT NULL,
  `house_number` VARCHAR(10) NOT NULL,
  `flat` SMALLINT UNSIGNED NOT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_address_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `cafe`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `cafe`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`category` ;

CREATE TABLE IF NOT EXISTS `cafe`.`category` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));


-- -----------------------------------------------------
-- Table `cafe`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`dish` ;

CREATE TABLE IF NOT EXISTS `cafe`.`dish` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `price` DECIMAL(5,2) NOT NULL,
  `weight` SMALLINT NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `picture_path` VARCHAR(150) NULL,
  `category_id` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_dish_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_dish_category1`
  FOREIGN KEY (`category_id`)
  REFERENCES `cafe`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `cafe`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`order` ;

CREATE TABLE IF NOT EXISTS `cafe`.`order` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `total_price` DECIMAL(5,2) NULL,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `number` INT(9) UNSIGNED ZEROFILL NOT NULL,
  `street` VARCHAR(150) NOT NULL,
  `house_number` VARCHAR(10) NOT NULL,
  `flat` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_order_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `cafe`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cafe`.`order_has_dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`order_has_dish` ;

CREATE TABLE IF NOT EXISTS `cafe`.`order_has_dish` (
  `order_id` INT(10) UNSIGNED NOT NULL,
  `dish_id` INT(10) UNSIGNED NOT NULL,
  `quantity` SMALLINT UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`order_id`, `dish_id`),
  INDEX `fk_order_has_dish_dish1_idx` (`dish_id` ASC),
  INDEX `fk_order_has_dish_order1_idx` (`order_id` ASC),
  CONSTRAINT `fk_order_has_dish_order1`
  FOREIGN KEY (`order_id`)
  REFERENCES `cafe`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_dish_dish1`
  FOREIGN KEY (`dish_id`)
  REFERENCES `cafe`.`dish` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
