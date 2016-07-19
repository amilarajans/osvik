package com.origins.osvik.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
@Entity
@Table(name = "INVOICE_NO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InvoiceNo implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "invoiceNo")
    private Long invoiceNo;

    @Size(max = 45)
    @Column(name = "poCode")
    private String poCode;

    @Column(name = "poDate")
    @Temporal(TemporalType.DATE)
    private Date poDate;

    @Size(max = 1)
    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "invoiceDate")
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "total")
    private Double total;

    @Column(name = "creditPeriod")
    private Integer creditPeriod;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rep_id", nullable = false)
    private Rep rep;

    @JsonIgnore
    @OneToMany(mappedBy = "invoiceNo")
    private List<Invoice> invoices;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(Long invoiceNo) {
        this.invoiceNo = invoiceNo;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Rep getRep() {
        return rep;
    }

    public void setRep(Rep rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return String.format("InvoiceNo{id=%d, invoiceNo=%d}", id, invoiceNo);
    }
}
