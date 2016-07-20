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
@Table(name = "CLIENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {
    private static final long serialVersionUID = 4295768790915153076L;
    @Id
    @Column(name = "idClient")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 45)
    @Column(name = "Code")
    private String code;

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

    @Size(max = 45)
    @Column(name = "web")
    private String web;

    @Size(max = 185)
    @Column(name = "Remark")
    private String remark;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<InvoiceNo> invoices;

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

    public List<InvoiceNo> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceNo> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return String.format("Client{id=%d}", id);
    }
}
