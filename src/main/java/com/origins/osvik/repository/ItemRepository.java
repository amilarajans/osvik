package com.origins.osvik.repository;

import com.origins.osvik.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "SELECT item FROM Item item WHERE item.code=:code")
    Item findOneByCode(@Param("code") String code);

    @Query(value = "SELECT new com.origins.osvik.domain.Item(item.id, item.code, item.category.name, item.subCategory.name, item.name, item.description, item.unit.name, item.supplier.name, item.country) FROM Item item")
    Page<Item> findAllByPage(Pageable pageable);

    @Override
    @Query(value = "SELECT new com.origins.osvik.domain.Item(item.id, item.code, item.category.name, item.subCategory.name, item.name, item.description, item.unit.name, item.supplier.name, item.country) FROM Item item")
    List<Item> findAll();
}
