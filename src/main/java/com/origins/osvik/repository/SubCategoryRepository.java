package com.origins.osvik.repository;

import com.origins.osvik.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public abstract interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    @Query(value = "SELECT subCategory FROM SubCategory subCategory WHERE subCategory.name=:name")
    SubCategory findOneByName(@Param("name") String name);
}
