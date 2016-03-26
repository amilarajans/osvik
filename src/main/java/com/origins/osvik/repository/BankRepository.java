package com.origins.osvik.repository;

import com.origins.osvik.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public abstract interface BankRepository extends JpaRepository<Bank, Integer> {
}
