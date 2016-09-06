package com.origins.osvik.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
@Entity
@Table(name = "STOCK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stock implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 20)
    @Column(name = "location")
    private String location;

    @Size(max = 20)
    @Column(name = "invoiceNo")
    private String invoiceNo;

    @Size(max = 20)
    @Column(name = "returnInvoiceNo")
    private String returnInvoiceNo;

    @Size(max = 45)
    @Column(name = "code")
    private String code;

    @Column(name = "qty")
    private Double qty;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Column(name = "price")
    private Double price;

    @Column(name = "costPrice")
    private Double costPrice;

    @Temporal(TemporalType.DATE)
    @Column(name = "mfd")
    private Date mfd;

    @Temporal(TemporalType.DATE)
    @Column(name = "doe")
    private Date doe;

    @Size(max = 45)
    @Column(name = "lotNo")
    private String lotNo;

    @Size(max = 45)
    @Column(name = "batchNo")
    private String batchNo;

    @Transient
    private String unitName;

    @Transient
    private Integer unitId;

    @Transient
    private String itemCode;

    @Transient
    private String itemName;

    @Transient
    private String supplier;

    public Stock() {
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDoe() {
        return doe;
    }

    public void setDoe(Date doe) {
        this.doe = doe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getReturnInvoiceNo() {
        return returnInvoiceNo;
    }

    public void setReturnInvoiceNo(String returnInvoiceNo) {
        this.returnInvoiceNo = returnInvoiceNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Date getMfd() {
        return mfd;
    }

    public void setMfd(Date mfd) {
        this.mfd = mfd;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    @Override
    public String toString() {
        return String.format("Stock{id=%d}", id);
    }
}
