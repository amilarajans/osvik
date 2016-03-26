package com.origins.osvik.dto;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
public class RepRepresentation {
    private String name;
    private String email;
    private String address;
    private String tel;
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return String.format("RepRepresentation{name='%s', email='%s', address='%s', tel='%s', remark='%s'}", name, email, address, tel, remark);
    }
}
