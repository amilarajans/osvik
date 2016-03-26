package com.origins.osvik.repository;

import com.origins.osvik.domain.Rep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public abstract interface RepRepository extends JpaRepository<Rep, Integer> {
    @Query(value = "SELECT rep FROM Rep rep WHERE rep.name=:name")
    Rep findOneByName(@Param("name") String name);
}
