package com.origins.osvik.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@Entity
@Table(name = "AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    @NotNull
    @Size(max = 50)
    @Id
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Authority authority = (Authority) o;
        if (this.name != null ? !this.name.equals(authority.name) : authority.name != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.name != null ? this.name.hashCode() : 0;
    }

    public String toString() {
        return String.format("Authority{name='%s'}", this.name);
    }
}