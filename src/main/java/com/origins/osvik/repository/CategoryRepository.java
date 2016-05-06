package com.origins.osvik.repository;

import com.origins.osvik.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT category FROM Category category WHERE category.name=:name")
    Category findOneByName(@Param("name") String name);

    @Query(value = "SELECT category FROM Category category WHERE category.name LIKE :name")
    Page<Category> findCategoryByName(@Param("name") String name, Pageable pageable);
}
