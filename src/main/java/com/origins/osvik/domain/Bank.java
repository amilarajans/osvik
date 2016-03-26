package com.origins.osvik.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
@Entity
@Table(name = "BANK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bank implements Serializable {
    private static final long serialVersionUID = -7250988024667577723L;
    @Id
    @Column(name = "idBank")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 115)
    @Column(name = "B_Name")
    private String name;

    @Size(max = 45)
    @Column(name = "AC_No")
    private String accNo;

    @Size(max = 45)
    @Column(name = "AC_Type")
    private String accType;

    @Size(max = 45)
    @Column(name = "Email")
    private String email;

    @Size(max = 45)
    @Column(name = "Bank_Tel")
    private String tel;

    @Size(max = 105)
    @Column(name = "remark")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        return String.format("Bank{id=%d, name='%s', accNo='%s', accType='%s', email='%s', tel='%s', remark='%s'}", id, name, accNo, accType, email, tel, remark);
    }
}
