package com.origins.osvik.repository;

import com.origins.osvik.domain.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    //String invoicenumber, String clientName, Date invoiceDate, String paymentMethod, Double total
    @Query(value = "SELECT new com.origins.osvik.dto.InvoiceDao(invoice.invoiceNo.invoiceNo,invoice.invoiceNo.client.name,invoice.invoiceNo.invoiceDate,invoice.invoiceNo.paymentMethod,invoice.invoiceNo.total) FROM Invoice invoice WHERE invoice.invoiceNo.invoiceNo = :invoiceNo OR (invoice.invoiceNo.client.code LIKE :clientCode AND invoice.invoiceNo.rep.name LIKE :repCode AND invoice.invoiceNo.paymentMethod = :paymentMethod) GROUP BY invoice.invoiceNo.invoiceNo")
    Page<Invoice> findAllByCriteria(@Param("clientCode") String clientCode, @Param("repCode") String repCode, @Param("paymentMethod") String paymentMethod, @Param("invoiceNo") Long invoiceNo, Pageable pageable);

    @Query(value = "SELECT new com.origins.osvik.dto.InvoiceDao(invoice.invoiceNo.invoiceNo,invoice.invoiceNo.client.name,invoice.invoiceNo.invoiceDate,invoice.invoiceNo.paymentMethod,invoice.invoiceNo.total) FROM Invoice invoice WHERE invoice.invoiceNo.invoiceNo = :invoiceNo OR (invoice.invoiceNo.client.code LIKE :clientCode AND invoice.invoiceNo.rep.name LIKE :repCode) GROUP BY invoice.invoiceNo.invoiceNo")
    Page<Invoice> findAllByCriteria(@Param("clientCode") String clientCode, @Param("repCode") String repCode, @Param("invoiceNo") Long invoiceNo, Pageable pageable);

}
