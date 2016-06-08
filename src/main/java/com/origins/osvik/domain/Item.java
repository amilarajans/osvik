package com.origins.osvik.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
@Entity
@Table(name = "ITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item implements Serializable {
    private static final long serialVersionUID = 5142510069953283639L;
    @Id
    @Column(name = "idItem")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 45)
    @Column(name = "I_code")
    private String code;

    @Size(max = 45)
    @Column(name = "Name")
    private String name;

    @Size(max = 45)
    @Column(name = "description")
    private String description;

    @Size(max = 45)
    @Column(name = "O_Country")
    private String country;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_idCategory", nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubCategory_idSubCategory", nullable = false)
    private SubCategory subCategory;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Unit_idUnit", nullable = false)
    private Unit unit;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Supplier_idSupplier", nullable = false)
    private Supplier supplier;

    @Transient
    private String categoryName;

    @Transient
    private String subCategoryName;

    @Transient
    private String unitName;

    @Transient
    private String supplierName;

    public Item() {
    }

    public Item(Integer id, String code, String categoryName, String subCategoryName, String name, String description, String unitName, String supplierName, String country) {
        this.id = id;
        this.code = code;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.name = name;
        this.description = description;
        this.unitName = unitName;
        this.supplierName = supplierName;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return String.format("Item{id=%d, code='%s', name='%s', description='%s', country='%s', category=%s, subCategory=%s, unit=%s, supplier=%s}", id, code, name, description, country, category, subCategory, unit, supplier);
    }
}
