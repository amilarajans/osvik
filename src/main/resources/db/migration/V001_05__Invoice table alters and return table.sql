ALTER TABLE INVOICE_NO
  ADD total DECIMAL NULL;

ALTER TABLE INVOICE_NO
  ADD rep_id INT NULL;
ALTER TABLE INVOICE_NO
  ADD CONSTRAINT invoice_no_medical_rep_idMedical_Rep_fk
FOREIGN KEY (rep_id) REFERENCES MEDICAL_REP (idMedical_Rep);

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
  CONSTRAINT return_invoice_item_iditem_fk FOREIGN KEY (return_item_id) REFERENCES ITEM (idItem),
  CONSTRAINT return_invoice_return_invoice_no_id_fk FOREIGN KEY (return_invoice_no) REFERENCES RETURN_INVOICE_NO (id)
);