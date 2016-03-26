package com.origins.osvik.dto;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public class SupplierRepresentation {

    private String code;
    private String name;
    private String originCountry;
    private String supplierAddress;
    private String supplierDesc;
    private String supplierEmail;
    private String supplierTel;
    private String supplierWeb;
    private String supplierType;

    public SupplierRepresentation() {
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

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierDesc() {
        return supplierDesc;
    }

    public void setSupplierDesc(String supplierDesc) {
        this.supplierDesc = supplierDesc;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
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

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    @Override
    public String toString() {
        return String.format("SupplierRepresentation{code='%s', name='%s', originCountry='%s', supplierAddress='%s', supplierDesc='%s', supplierEmail='%s', supplierTel='%s', supplierWeb='%s', supplierType='%s'}", code, name, originCountry, supplierAddress, supplierDesc, supplierEmail, supplierTel, supplierWeb, supplierType);
    }
}