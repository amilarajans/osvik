-- -----------------------------------------------------
-- Table `OSVIK`.`STOCK`
-- -----------------------------------------------------
CREATE TABLE STOCK
(
  id        INT PRIMARY KEY AUTO_INCREMENT,
  location  VARCHAR(20),
  invoiceNo VARCHAR(20),
  code      VARCHAR(45),
  qty       INT,
  unit_id   INT NOT NULL,
  price     DOUBLE,
  mfd       DATE,
  doe       DATE,
  lotNo     VARCHAR(45),
  batchNo   VARCHAR(45),
  CONSTRAINT STOCK_unit_idunit_fk FOREIGN KEY (unit_id) REFERENCES UNIT (idunit)
)