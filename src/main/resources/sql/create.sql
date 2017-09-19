-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
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
-- Table `cafe`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`category` ;

CREATE TABLE IF NOT EXISTS `cafe`.`category` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(150) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)  COMMENT '')
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cafe`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`dish` ;

CREATE TABLE IF NOT EXISTS `cafe`.`dish` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(150) NOT NULL COMMENT '',
  `price` DECIMAL(5,2) NOT NULL COMMENT '',
  `weight` SMALLINT NOT NULL COMMENT '',
  `description` TINYTEXT NOT NULL COMMENT '',
  `picture_path` VARCHAR(150) NULL COMMENT '',
  `category_id` TINYINT UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_dish_category1_idx` (`category_id` ASC)  COMMENT '',
  CONSTRAINT `fk_dish_category1`
  FOREIGN KEY (`category_id`)
  REFERENCES `cafe`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cafe`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`role` ;

CREATE TABLE IF NOT EXISTS `cafe`.`role` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(150) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)  COMMENT '')
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cafe`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`user` ;

CREATE TABLE IF NOT EXISTS `cafe`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `login` VARCHAR(150) NOT NULL COMMENT '',
  `password` VARCHAR(150) NOT NULL COMMENT '',
  `account_balance` DECIMAL(5,2) NOT NULL DEFAULT '0.00' COMMENT '',
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  `role_id` TINYINT UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `login_UNIQUE` (`login` ASC)  COMMENT '',
  INDEX `fk_user_role1_idx` (`role_id` ASC)  COMMENT '',
  CONSTRAINT `fk_user_role1`
  FOREIGN KEY (`role_id`)
  REFERENCES `cafe`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cafe`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`order` ;

CREATE TABLE IF NOT EXISTS `cafe`.`order` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `total_price` DECIMAL(5,2) NULL COMMENT '',
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  `number` INT UNSIGNED ZEROFILL NOT NULL COMMENT '',
  `street` VARCHAR(150) NOT NULL COMMENT '',
  `house_number` VARCHAR(10) NOT NULL COMMENT '',
  `flat` SMALLINT UNSIGNED NOT NULL COMMENT '',
  `user_id` INT UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_order_user1_idx` (`user_id` ASC)  COMMENT '',
  CONSTRAINT `fk_order_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `cafe`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cafe`.`user_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`user_info` ;

CREATE TABLE IF NOT EXISTS `cafe`.`user_info` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `number` INT UNSIGNED ZEROFILL NOT NULL COMMENT '',
  `street` VARCHAR(150) NOT NULL COMMENT '',
  `house_number` VARCHAR(10) NOT NULL COMMENT '',
  `flat` SMALLINT UNSIGNED NOT NULL COMMENT '',
  `user_id` INT UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_user_info_user1_idx` (`user_id` ASC)  COMMENT '',
  CONSTRAINT `fk_user_info_user1`
  FOREIGN KEY (`user_id`)
  REFERENCES `cafe`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cafe`.`order_has_dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`order_has_dish` ;

CREATE TABLE IF NOT EXISTS `cafe`.`order_has_dish` (
  `order_id` INT UNSIGNED NOT NULL COMMENT '',
  `dish_id` INT UNSIGNED NOT NULL COMMENT '',
  `quantity` TINYINT UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`order_id`, `dish_id`)  COMMENT '',
  INDEX `fk_order_has_dish_dish1_idx` (`dish_id` ASC)  COMMENT '',
  INDEX `fk_order_has_dish_order1_idx` (`order_id` ASC)  COMMENT '',
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
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cafe`.`shopping_cart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cafe`.`shopping_cart` ;

CREATE TABLE IF NOT EXISTS `cafe`.`shopping_cart` (
  `user_id` INT UNSIGNED NOT NULL COMMENT '',
  `dish_id` INT UNSIGNED NOT NULL COMMENT '',
  `quantity` TINYINT UNSIGNED NOT NULL COMMENT '',
  PRIMARY KEY (`user_id`, `dish_id`)  COMMENT '',
  INDEX `fk_user_has_dish_dish2_idx` (`dish_id` ASC)  COMMENT '',
  INDEX `fk_user_has_dish_user2_idx` (`user_id` ASC)  COMMENT '',
  CONSTRAINT `fk_user_has_dish_user2`
  FOREIGN KEY (`user_id`)
  REFERENCES `cafe`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_dish_dish2`
  FOREIGN KEY (`dish_id`)
  REFERENCES `cafe`.`dish` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
