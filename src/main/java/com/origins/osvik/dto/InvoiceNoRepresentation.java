package com.origins.osvik.dto;

import com.origins.osvik.enums.PaymentMethods;

import java.io.Serializable;

/**
 * Created by Amila-Kumara on 20/06/2016.
 */
public class InvoiceNoRepresentation implements Serializable {
    private static final long serialVersionUID = -4290777141536632872L;
    private Double discount;
    private String poCode;
    private String paymentMethod;
    private String clientName;
    private Double total;
    private Long invoiceNo;

    public InvoiceNoRepresentation(String poCode, String paymentMethod, String clientName, Double total, Long invoiceNo) {
        this.poCode = poCode;
        if (paymentMethod == null || paymentMethod.equals("0")) {
            this.paymentMethod = "N/A";
        } else {
            this.paymentMethod = PaymentMethods.getByCode(Integer.parseInt(paymentMethod)).getName();
        }
        this.clientName = clientName;
        this.total = total;
        this.invoiceNo = invoiceNo;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(Long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Override
    public String toString() {
        return String.format("InvoiceNoRepresentation{discount=%s, poCode='%s', paymentMethod='%s', clientName='%s', total=%s, invoiceNo=%d}", discount, poCode, paymentMethod, clientName, total, invoiceNo);
    }
}
