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
@Table(name = "SUBCATEGORY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubCategory implements Serializable {
    private static final long serialVersionUID = -2191066833380002994L;
    @Id
    @Column(name = "idSubCategory")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 105)
    @Column(name = "SC_Name")
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_idCategory", nullable = false)
    private Category category;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCategory")
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return String.format("SubCategory{id=%d, name='%s', category=%s, items=%s}", id, name, category, items);
    }
}
