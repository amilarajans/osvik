package com.origins.osvik.dto;

import com.origins.osvik.domain.Invoice;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Amila-Kumara on 20/06/2016.
 */
public class ReturnInvoiceRepresentation implements Serializable {
    private Long invoiceNo;
    private String causeOfReturn;
    private Date returnDate;
    private List<Invoice> returnItemsList;

    public Long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(Long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getCauseOfReturn() {
        return causeOfReturn;
    }

    public void setCauseOfReturn(String causeOfReturn) {
        this.causeOfReturn = causeOfReturn;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public List<Invoice> getReturnItemsList() {
        return returnItemsList;
    }

    public void setReturnItemsList(List<Invoice> returnItemsList) {
        this.returnItemsList = returnItemsList;
    }

    @Override
    public String toString() {
        return String.format("ReturnInvoiceRepresentation{invoiceNo=%d, causeOfReturn='%s', returnDate=%s, returnItemsList=%s}", invoiceNo, causeOfReturn, returnDate, returnItemsList);
    }
}
