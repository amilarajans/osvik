package com.origins.osvik.dto;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public class BankRepresentation {
    private String name;
    private String accNo;
    private String accType;
    private String email;
    private String tel;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return String.format("BankRepresentation{name='%s', accNo='%s', accType='%s', email='%s', tel='%s', remark='%s'}", name, accNo, accType, email, tel, remark);
    }
}