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
@Table(name = "CATEGORY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {
    private static final long serialVersionUID = 5192923389740154418L;
    @Id
    @Column(name = "idCategory")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @Column(name = "C_Name")
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<SubCategory> SubCategory;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Item> items;

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

    public List<SubCategory> getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        SubCategory = subCategory;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return String.format("Category{id=%d, name='%s', SubCategory=%s, items=%s}", id, name, SubCategory, items);
    }
}
