package com.origins.osvik.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
@Entity
@Table(name = "SUPPLIER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Supplier implements Serializable {
    private static final long serialVersionUID = -4780899431362812767L;
    @Id
    @Column(name = "idSupplier")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 45)
    @Column(name = "Code")
    private String code;

    @Size(max = 145)
    @Column(name = "Name")
    private String name;

    @Size(max = 45)
    @Column(name = "O_Country")
    private String originCountry;

    @Email
    @Size(max = 115)
    @Column(name = "S_email")
    private String supplierEmail;

    @Size(max = 155)
    @Column(name = "S_Address")
    private String supplierAddress;

    @Size(max = 45)
    @Column(name = "S_tel")
    private String supplierTel;

    @Size(max = 45)
    @Column(name = "S_web")
    private String supplierWeb;

    @Size(max = 185)
    @Column(name = "S_Desc")
    private String supplierDesc;

    @Size(max = 45)
    @Column(name = "S_type")
    private String supplierType;

    @Size(max = 45)
    @Column(name = "S_C_Person")
    private String supplierContactPerson;

    @Size(max = 45)
    @Column(name = "S_C_tel")
    private String supplierContactTel;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCategory")
    private List<Item> items;

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

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierTel() {
        return supplierTel;
    }

    public void setSupplierTel(String supplierTel) {
        this.supplierTel = supplierTel;
    }

    public String getSupplierWeb() {
        return supplierWeb;
    }

    public void setSupplierWeb(String supplierWeb) {
        this.supplierWeb = supplierWeb;
    }

    public String getSupplierDesc() {
        return supplierDesc;
    }

    public void setSupplierDesc(String supplierDesc) {
        this.supplierDesc = supplierDesc;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getSupplierContactPerson() {
        return supplierContactPerson;
    }

    public void setSupplierContactPerson(String supplierContactPerson) {
        this.supplierContactPerson = supplierContactPerson;
    }

    public String getSupplierContactTel() {
        return supplierContactTel;
    }

    public void setSupplierContactTel(String supplierContactTel) {
        this.supplierContactTel = supplierContactTel;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return String.format("Supplier{id=%d, code='%s', name='%s', originCountry='%s', supplierEmail='%s', supplierAddress='%s', supplierTel='%s', supplierWeb='%s', supplierDesc='%s', supplierType='%s', supplierContactPerson='%s', supplierContactTel='%s', items=%s}", id, code, name, originCountry, supplierEmail, supplierAddress, supplierTel, supplierWeb, supplierDesc, supplierType, supplierContactPerson, supplierContactTel, items);
    }
}
