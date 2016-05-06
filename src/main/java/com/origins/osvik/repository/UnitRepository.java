package com.origins.osvik.repository;

import com.origins.osvik.domain.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    @Query(value = "SELECT unit FROM Unit unit WHERE unit.name=:name")
    Unit findOneByName(@Param("name") String name);

    @Query(value = "SELECT unit FROM Unit unit WHERE unit.name LIKE :name")
    Page<Unit> findUnitByName(@Param("name") String name, Pageable pageable);
}
