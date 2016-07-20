package com.origins.osvik.repository;

import com.origins.osvik.domain.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface BankRepository extends JpaRepository<Bank, Integer> {

    @Query(value = "SELECT bank FROM Bank bank WHERE bank.name LIKE :name")
    Page<Bank> findBankByName(@Param("name") String name, Pageable pageable);
}
