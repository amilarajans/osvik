package com.origins.osvik.repository;

import com.origins.osvik.domain.Item;
import com.origins.osvik.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public abstract interface ItemRepository extends JpaRepository<Item, String> {
    @Query(value = "SELECT item FROM Item item WHERE item.code=:code")
    Item findOneByCode(@Param("code") String code);
}
