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
@Table(name = "RETURN_INVOICE_NO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReturnInvoiceNo implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "invoiceNo")
    private Long invoiceNo;

    @Column(name = "returnInvoiceNo")
    private Long returnInvoiceNo;

    @Size(max = 1)
    @Column(name = "causeOfReturn")
    private String causeOfReturn;

    @Column(name = "returnDate")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @JsonIgnore
    @OneToMany(mappedBy = "invoiceNo")
    private List<ReturnInvoice> invoices;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(Long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getReturnInvoiceNo() {
        return returnInvoiceNo;
    }

    public void setReturnInvoiceNo(Long returnInvoiceNo) {
        this.returnInvoiceNo = returnInvoiceNo;
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

    public List<ReturnInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<ReturnInvoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return String.format("ReturnInvoiceNo{id=%d, invoiceNo=%d, returnInvoiceNo=%d}", id, invoiceNo, returnInvoiceNo);
    }
}
