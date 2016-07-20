package com.origins.osvik.dto;

import com.origins.osvik.domain.Stock;

import java.util.Date;

/**
 * Created by Amila-Kumara on 3/21/2016.
 */
public class StockRepresentation extends Stock {
    private Integer id;
    private String name;
    private String code;
    private Double qty;
    private Date doe;
    private String lotNo;
    private String batchNo;
    private String supplier;
    private Double price;
    private String desc;

    public StockRepresentation(Integer id, String itemCode, String itemName, Double qty, Date doe, String lotNo, String batchNo, String desc, Double price, String supplier) {
        this.batchNo = batchNo;
        this.code = itemCode;
        this.doe = doe;
        this.lotNo = lotNo;
        this.name = itemName;
        this.price = price;
        this.qty = qty;
        this.desc = desc;
        this.id = id;
        this.supplier = supplier;
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

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return String.format("StockRepresentation{id=%d}", id);
    }
}
