package com.origins.osvik.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
@Entity
@Table(name = "INVOICE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invoice implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "unitPrice")
    private Double unitPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_no", nullable = false)
    private InvoiceNo invoiceNo;

    @Transient
    private String clientCode;

    @Transient
    private String itemCode;

    @Transient
    private Integer stockId;

    public Invoice() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public InvoiceNo getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(InvoiceNo invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    @Override
    public String toString() {
        return String.format("Invoice{clientCode='%s', id=%d, qty=%s, unitPrice=%s, itemCode='%s', stockId=%d}", clientCode, id, qty, unitPrice, itemCode, stockId);
    }
}
