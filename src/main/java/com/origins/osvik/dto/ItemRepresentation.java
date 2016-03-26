package com.origins.osvik.dto;

/**
 * Created by Amila-Kumara on 3/21/2016.
 */
public class ItemRepresentation {
    private String name;
    private String code;
    private Integer category;
    private Integer subCategory;
    private Integer unit;
    private Integer supplier;
    private String categoryName;
    private String subCategoryName;
    private String unitName;
    private String supplierName;
    private String description;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Integer subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("ItemRepresentation{name='%s', code='%s', category=%d, subCategory=%d, unit=%d, supplier=%d, categoryName='%s', subCategoryName='%s', unitName='%s', supplierName='%s', description='%s', country='%s'}", name, code, category, subCategory, unit, supplier, categoryName, subCategoryName, unitName, supplierName, description, country);
    }
}
