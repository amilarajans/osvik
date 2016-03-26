-- -----------------------------------------------------
-- Table `OSVIK`.`Supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`Supplier` (
  `idSupplier` INT          NOT NULL AUTO_INCREMENT,
  `Code`       VARCHAR(45)  NULL,
  `Name`       VARCHAR(145) NULL,
  `O_Country`  VARCHAR(45)  NULL,
  `S_email`    VARCHAR(115) NULL,
  `S_Address`  VARCHAR(155) NULL,
  `S_tel`      VARCHAR(45)  NULL,
  `S_web`      VARCHAR(45)  NULL,
  `S_Desc`     VARCHAR(185) NULL,
  `S_type`     VARCHAR(45)  NULL,
  `S_C_Person` VARCHAR(45)  NULL,
  `S_C_tel`    VARCHAR(45)  NULL,
  PRIMARY KEY (`idSupplier`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `OSVIK`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`Client` (
  `idClient` INT          NOT NULL AUTO_INCREMENT,
  `Code`     VARCHAR(45)  NULL,
  `Name`     VARCHAR(145) NULL,
  `email`    VARCHAR(115) NULL,
  `Address`  VARCHAR(155) NULL,
  `tel`      VARCHAR(45)  NULL,
  `web`      VARCHAR(45)  NULL,
  `Remark`   VARCHAR(185) NULL,
  PRIMARY KEY (`idClient`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `OSVIK`.`Medical_Rep`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`Medical_Rep` (
  `idMedical_Rep` INT          NOT NULL AUTO_INCREMENT,
  `Name`          VARCHAR(45)  NULL,
  `Email`         VARCHAR(45)  NULL,
  `Address`       VARCHAR(45)  NULL,
  `Tel`           VARCHAR(45)  NULL,
  `Remark`        VARCHAR(105) NULL,
  PRIMARY KEY (`idMedical_Rep`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `OSVIK`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`Category` (
  `idCategory` INT          NOT NULL AUTO_INCREMENT,
  `C_Name`     VARCHAR(100) NULL,
  PRIMARY KEY (`idCategory`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `OSVIK`.`SubCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`SubCategory` (
  `idSubCategory`       INT          NOT NULL AUTO_INCREMENT,
  `SC_Name`             VARCHAR(105) NULL,
  `Category_idCategory` INT          NOT NULL,
  PRIMARY KEY (`idSubCategory`),
  INDEX `fk_SubCategory_Category` (`Category_idCategory` ASC),
  CONSTRAINT `fk_SubCategory_Category`
  FOREIGN KEY (`Category_idCategory`)
  REFERENCES `OSVIK`.`Category` (`idCategory`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `OSVIK`.`unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`unit` (
  `idunit`    INT         NOT NULL AUTO_INCREMENT,
  `Unit_Name` VARCHAR(45) NULL,
  PRIMARY KEY (`idunit`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `OSVIK`.`Bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`Bank` (
  `idBank`   INT          NOT NULL AUTO_INCREMENT,
  `B_Name`   VARCHAR(115) NULL,
  `AC_No`    VARCHAR(45)  NULL,
  `AC_Type`  VARCHAR(45)  NULL,
  `Email`    VARCHAR(45)  NULL,
  `Bank_Tel` VARCHAR(45)  NULL,
  `remark`   VARCHAR(105) NULL,
  PRIMARY KEY (`idBank`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `OSVIK`.`B_Con_person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`B_Con_person` (
  `idB_Con_person` INT         NOT NULL AUTO_INCREMENT,
  `Name`           VARCHAR(45) NULL,
  `Tel`            VARCHAR(45) NULL,
  `Postion`        VARCHAR(45) NULL,
  `Bank_idBank`    INT         NOT NULL,
  PRIMARY KEY (`idB_Con_person`),
  INDEX `fk_B_Con_person_Bank1` (`Bank_idBank` ASC),
  CONSTRAINT `fk_B_Con_person_Bank1`
  FOREIGN KEY (`Bank_idBank`)
  REFERENCES `OSVIK`.`Bank` (`idBank`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `OSVIK`.`Item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OSVIK`.`Item` (
  `idItem`                    INT         NOT NULL AUTO_INCREMENT,
  `I_code`                    VARCHAR(45) NULL,
  `Category_idCategory`       INT         NOT NULL,
  `SubCategory_idSubCategory` INT         NOT NULL,
  `Supplier_idSupplier`       INT         NOT NULL,
  `Name`                      VARCHAR(45) NULL,
  `unit_idunit`               INT         NOT NULL,
  `description`               VARCHAR(45) NULL,
  `O_Country`                 VARCHAR(45) NULL,
  PRIMARY KEY (`idItem`),
  INDEX `fk_Item_Category1` (`Category_idCategory` ASC),
  INDEX `fk_Item_unit1` (`unit_idunit` ASC),
  CONSTRAINT `fk_Item_Category1`
  FOREIGN KEY (`Category_idCategory`)
  REFERENCES `OSVIK`.`Category` (`idCategory`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Item_unit1`
  FOREIGN KEY (`Unit_idUnit`)
  REFERENCES `OSVIK`.`unit` (`idunit`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Item_SubCategory`
  FOREIGN KEY (`SubCategory_idSubCategory`)
  REFERENCES `OSVIK`.`SubCategory` (`idSubCategory`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Item_Supplier`
  FOREIGN KEY (`Supplier_idSupplier`)
  REFERENCES `OSVIK`.`Supplier` (`idSupplier`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  COMMENT = '	\n';