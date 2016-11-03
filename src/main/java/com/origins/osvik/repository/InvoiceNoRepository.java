package com.origins.osvik.repository;

import com.origins.osvik.domain.InvoiceNo;
import com.origins.osvik.dto.InvoiceNoRepresentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface InvoiceNoRepository extends JpaRepository<InvoiceNo, Integer> {

    @Query(value = "SELECT new com.origins.osvik.dto.InvoiceNoRepresentation(invoice.poCode,invoice.paymentMethod,invoice.client.name,invoice.total,invoice.invoiceNo) FROM InvoiceNo invoice ORDER BY invoice.invoiceNo DESC ")
    List<InvoiceNoRepresentation> findAllInvoice();

    @Query(value = "SELECT invoice FROM InvoiceNo invoice WHERE invoice.invoiceNo=:invoiceNo ")
    InvoiceNo findInvoiceNoByInvoiceNo(@Param("invoiceNo") Long invoiceNo);
}
