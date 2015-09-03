-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`personagem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`personagem` ;

CREATE TABLE IF NOT EXISTS `mydb`.`personagem` (
  `id_personagem` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `path_imagem` TEXT NOT NULL,
  `path_hd` TEXT NOT NULL,
  `min` INT NULL,
  `max` INT NULL,
  PRIMARY KEY (`id_personagem`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`curso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`curso` ;

CREATE TABLE IF NOT EXISTS `mydb`.`curso` (
  `id_curso` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_curso`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`usuario` ;

CREATE TABLE IF NOT EXISTS `mydb`.`usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `login` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `tipo_usuario` ENUM('administrador', 'revisor','descritor_pontuavel', 'revisor_pontuavel') NOT NULL,
  `pontos` INT NULL DEFAULT 0,
  `promovido` TINYINT(1) NULL DEFAULT 0,
  `id_personagem` INT NULL,
  `id_curso` INT NULL,
  PRIMARY KEY (`id_usuario`),
  INDEX `fk_usuario_personagem_idx` (`id_personagem` ASC),
  INDEX `fk_usuario_curso1_idx` (`id_curso` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  CONSTRAINT `fk_usuario_personagem`
    FOREIGN KEY (`id_personagem`)
    REFERENCES `mydb`.`personagem` (`id_personagem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_curso1`
    FOREIGN KEY (`id_curso`)
    REFERENCES `mydb`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`autor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`autor` ;

CREATE TABLE IF NOT EXISTS `mydb`.`autor` (
  `id_autor` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NULL,
  PRIMARY KEY (`id_autor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`livro`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`livro` ;

CREATE TABLE IF NOT EXISTS `mydb`.`livro` (
  `id_livro` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(255) NOT NULL,
  `editora` VARCHAR(255) NOT NULL,
  `edicao` VARCHAR(100) NOT NULL,
  `ano_publicacao` INT NULL,
  `id_autor` INT NOT NULL,
  PRIMARY KEY (`id_livro`),
  INDEX `fk_livro_autor1_idx` (`id_autor` ASC),
  CONSTRAINT `fk_livro_autor1`
    FOREIGN KEY (`id_autor`)
    REFERENCES `mydb`.`autor` (`id_autor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`capitulo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`capitulo` ;

CREATE TABLE IF NOT EXISTS `mydb`.`capitulo` (
  `id_capitulo` INT NOT NULL AUTO_INCREMENT,
  `numero` INT NULL,
  `path_capitulo` VARCHAR(255) NOT NULL,
  `id_livro` INT NOT NULL,
  PRIMARY KEY (`id_capitulo`),
  INDEX `fk_capitulo_livro1_idx` (`id_livro` ASC),
  UNIQUE INDEX `path_capitulo_UNIQUE` (`path_capitulo` ASC),
  CONSTRAINT `fk_capitulo_livro1`
    FOREIGN KEY (`id_livro`)
    REFERENCES `mydb`.`livro` (`id_livro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`pagina`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`pagina` ;

CREATE TABLE IF NOT EXISTS `mydb`.`pagina` (
  `id_pagina` INT NOT NULL AUTO_INCREMENT,
  `id_capitulo` INT NOT NULL,
  `numero` INT NULL,
  PRIMARY KEY (`id_pagina`, `id_capitulo`),
  INDEX `fk_pagina_capitulo1_idx` (`id_capitulo` ASC),
  CONSTRAINT `fk_pagina_capitulo1`
    FOREIGN KEY (`id_capitulo`)
    REFERENCES `mydb`.`capitulo` (`id_capitulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`imagem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`imagem` ;

CREATE TABLE IF NOT EXISTS `mydb`.`imagem` (
  `id_imagem` INT NOT NULL AUTO_INCREMENT,
  `id_pagina` INT NOT NULL,
  `estado` INT NOT NULL,
  `path_imagem` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_imagem`),
  INDEX `fk_imagem_pagina1_idx` (`id_pagina` ASC),
  UNIQUE INDEX `path_imagem_UNIQUE` (`path_imagem` ASC),
  CONSTRAINT `fk_imagem_pagina1`
    FOREIGN KEY (`id_pagina`)
    REFERENCES `mydb`.`pagina` (`id_pagina`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`descricao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`descricao` ;

CREATE TABLE IF NOT EXISTS `mydb`.`descricao` (
  `id_descricao` INT NOT NULL AUTO_INCREMENT,
  `id_imagem` INT NOT NULL,
  `id_descritor` INT NOT NULL,
  `id_revisor` INT NULL,
  `estado` INT NOT NULL,
  `texto` TEXT NULL,
  `texto_backup` TEXT NULL,
  `ultima_mod_texto` DATETIME NULL,
  `ultima_mod_backup` DATETIME NULL,
  PRIMARY KEY (`id_descricao`),
  INDEX `fk_descricao_usuario1_idx` (`id_revisor` ASC),
  INDEX `fk_descricao_imagem1_idx` (`id_imagem` ASC),
  INDEX `fk_descricao_usuario2_idx` (`id_descritor` ASC),
  CONSTRAINT `fk_descricao_usuario1`
    FOREIGN KEY (`id_revisor`)
    REFERENCES `mydb`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_descricao_imagem1`
    FOREIGN KEY (`id_imagem`)
    REFERENCES `mydb`.`imagem` (`id_imagem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_descricao_usuario2`
    FOREIGN KEY (`id_descritor`)
    REFERENCES `mydb`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`livro_pertence_curso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`livro_pertence_curso` ;

CREATE TABLE IF NOT EXISTS `mydb`.`livro_pertence_curso` (
  `id_livro` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `estado` INT NOT NULL,
  `path` VARCHAR(255) NULL,
  PRIMARY KEY (`id_livro`, `id_curso`),
  INDEX `fk_livro_has_curso_curso1_idx` (`id_curso` ASC),
  INDEX `fk_livro_has_curso_livro1_idx` (`id_livro` ASC),
  CONSTRAINT `fk_livro_has_curso_livro1`
    FOREIGN KEY (`id_livro`)
    REFERENCES `mydb`.`livro` (`id_livro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_has_curso_curso1`
    FOREIGN KEY (`id_curso`)
    REFERENCES `mydb`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
