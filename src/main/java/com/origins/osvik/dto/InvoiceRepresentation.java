package com.origins.osvik.dto;

import com.origins.osvik.domain.Invoice;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Amila-Kumara on 20/06/2016.
 */
public class InvoiceRepresentation implements Serializable {
    private Double discount;
    private Double totalPrice;
    private Integer creditPeriod;
    private String poCode;
    private String paymentMethod;
    private String clientCode;
    private Date poDate;
    private List<Invoice> invoices;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(Integer creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public Date getPoDate() {
        return poDate;
    }

    public void setPoDate(Date poDate) {
        this.poDate = poDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return String.format("InvoiceRepresentation{clientCode='%s', discount=%s, creditPeriod=%d, poCode='%s', paymentMethod='%s', poDate=%s}", clientCode, discount, creditPeriod, poCode, paymentMethod, poDate);
    }
}
