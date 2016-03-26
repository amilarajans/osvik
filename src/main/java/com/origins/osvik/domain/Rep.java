package com.origins.osvik.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
@Entity
@Table(name = "MEDICAL_REP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rep implements Serializable {
    private static final long serialVersionUID = -7271055530598838477L;
    @Id
    @Column(name = "idMedical_Rep")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 145)
    @Column(name = "Name")
    private String name;

    @Email
    @Size(max = 115)
    @Column(name = "email")
    private String email;

    @Size(max = 155)
    @Column(name = "Address")
    private String address;

    @Size(max = 45)
    @Column(name = "tel")
    private String tel;

    @Size(max = 185)
    @Column(name = "Remark")
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
        return String.format("Rep{id=%d, name='%s', email='%s', address='%s', tel='%s', remark='%s'}", id, name, email, address, tel, remark);
    }
}
