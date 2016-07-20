package com.origins.osvik.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
@Entity
@Table(name = "UNIT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Unit implements Serializable {
    private static final long serialVersionUID = 7844995646481550462L;
    @Id
    @Column(name = "idunit")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 45)
    @Column(name = "Unit_Name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="unit")
    private List<Item> items;

    @JsonIgnore
    @OneToMany(mappedBy = "unit")
    private List<Stock> stocks;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return String.format("Unit{id=%d, name='%s', items=%s}", id, name, items);
    }
}
