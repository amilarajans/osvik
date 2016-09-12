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
    private Double costPrice;
    private String desc;
    private String category;
    private String categoryName;
    private String subCategoryName;

    public StockRepresentation(Integer id, String itemCode, String itemName, Double qty, Date doe, String lotNo, String batchNo, String desc, Double price, String supplier, String category) {
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
        this.category = category;
    }

    public StockRepresentation(Integer id, String itemCode, String itemName, Double qty, Date doe, String lotNo, String batchNo, String desc, Double price, String supplier, String category, String invoiceNo, String location, String unitName, String categoryName, String subCategoryName,Double costPrice) {
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
        this.category = category;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.costPrice = costPrice;
        this.setInvoiceNo(invoiceNo);
        this.setLocation(location);
        this.setUnitName(unitName);
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

    @Override
    public Double getCostPrice() {
        return costPrice;
    }

    @Override
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    @Override
    public String toString() {
        return String.format("StockRepresentation{id=%d}", id);
    }
}
