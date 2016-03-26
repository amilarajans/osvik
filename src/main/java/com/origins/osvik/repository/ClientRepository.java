package com.origins.osvik.repository;

import com.origins.osvik.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public abstract interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query(value = "SELECT client FROM Client client WHERE client.code=:code")
    Client findOneByCode(@Param("code") String code);
}
