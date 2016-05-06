package com.origins.osvik.repository;

import com.origins.osvik.domain.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    @Query(value = "SELECT supplier FROM Supplier supplier WHERE supplier.code=:code")
    Supplier findOneByCode(@Param("code") String code);

    @Query(value = "SELECT supplier FROM Supplier supplier WHERE supplier.name LIKE :name")
    Page<Supplier> findSupplierByName(@Param("name") String name, Pageable pageable);

}
