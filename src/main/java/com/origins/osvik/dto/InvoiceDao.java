package com.origins.osvik.dto;

import com.origins.osvik.domain.Invoice;
import com.origins.osvik.enums.PaymentMethods;

import java.util.Date;

/**
 * Created by Amila-Kumara on 13/08/2016.
 */
public class InvoiceDao extends Invoice {

    private Long invoiceNumber;
    private String clientName;
    private Date invoiceDate;
    private String paymentMethod;
    private Double total;

    public InvoiceDao(Long invoiceNumber, String clientName, Date invoiceDate, String paymentMethod, Double total) {
        this.invoiceNumber = invoiceNumber;
        this.clientName = clientName;
        this.invoiceDate = invoiceDate;
        if (paymentMethod == null || paymentMethod.equals("0")) {
            this.paymentMethod = "N/A";
        } else {
            this.paymentMethod = PaymentMethods.getByCode(Integer.parseInt(paymentMethod)).getName();
        }
        this.total = total;
    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("InvoiceDao{invoiceNumber=%d}", invoiceNumber);
    }
}
