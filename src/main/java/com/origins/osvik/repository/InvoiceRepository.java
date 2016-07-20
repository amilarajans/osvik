package com.origins.osvik.repository;

import com.origins.osvik.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}
