package com.origins.osvik.dto;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
public class ClientRepresentation {
    private String code;
    private String name;
    private String email;
    private String address;
    private String tel;
    private String web;
    private String remark;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return String.format("ClientRepresentation{code='%s', name='%s', email='%s', address='%s', tel='%s', web='%s', remark='%s'}", code, name, email, address, tel, web, remark);
    }
}
