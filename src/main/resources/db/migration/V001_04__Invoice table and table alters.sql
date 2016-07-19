ALTER TABLE ITEM
  MODIFY description VARCHAR(100);
ALTER TABLE STOCK
  MODIFY qty DECIMAL;

CREATE TABLE INVOICE_NO
(
  id            INT PRIMARY KEY AUTO_INCREMENT,
  invoiceNo     INT,
  poCode        VARCHAR(45),
  poDate        DATE,
  paymentMethod VARCHAR(1),
  invoiceDate   DATETIME,
  discount      DECIMAL,
  creditPeriod  INT,
  client_id     INT,
  CONSTRAINT invoice_client_idClient_fk FOREIGN KEY (client_id) REFERENCES CLIENT (idClient)
);

CREATE TABLE INVOICE
(
  id         INT PRIMARY KEY AUTO_INCREMENT,
  qty        DECIMAL,
  unitPrice  DECIMAL,
  item_id    INT,
  invoice_no INT,
  CONSTRAINT invoice_item_idItem_fk FOREIGN KEY (item_id) REFERENCES ITEM (idItem),
  CONSTRAINT invoice_invoice_no_id_fk FOREIGN KEY (invoice_no) REFERENCES INVOICE_NO (id)
);