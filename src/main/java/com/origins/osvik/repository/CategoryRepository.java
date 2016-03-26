package com.origins.osvik.repository;

import com.origins.osvik.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public abstract interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT category FROM Category category WHERE category.name=:name")
    Category findOneByName(@Param("name") String name);
}
