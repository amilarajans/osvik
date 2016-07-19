ALTER TABLE INVOICE_NO
  ADD total DECIMAL NULL;

ALTER TABLE STOCK
  ADD returnInvoiceNo VARCHAR(20) NULL;

CREATE TABLE RETURN_INVOICE_NO
(
  id              INT PRIMARY KEY AUTO_INCREMENT,
  invoiceNo       INT,
  returnInvoiceNo INT,
  causeOfReturn   VARCHAR(1),
  returnDate      DATE
);

CREATE TABLE RETURN_INVOICE
(
  id                INT PRIMARY KEY AUTO_INCREMENT,
  qty               DECIMAL,
  unitPrice         DECIMAL,
  return_item_id    INT,
  return_invoice_no INT,
  CONSTRAINT RETURN_INVOICE_item_idItem_fk FOREIGN KEY (return_item_id) REFERENCES ITEM (idItem),
  CONSTRAINT RETURN_INVOICE_return_invoice_no_id_fk FOREIGN KEY (return_invoice_no) REFERENCES RETURN_INVOICE_NO (id)
);