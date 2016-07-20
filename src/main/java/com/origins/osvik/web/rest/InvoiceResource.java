package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.Invoice;
import com.origins.osvik.domain.InvoiceNo;
import com.origins.osvik.domain.Stock;
import com.origins.osvik.dto.InvoiceRepresentation;
import com.origins.osvik.dto.Page;
import com.origins.osvik.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@RestController
@RequestMapping(value = "api/v1/invoice")
public class InvoiceResource {
    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    @Autowired
    private Environment env;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RepRepository repRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private InvoiceNoRepository invoiceNoRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Invoice> getAllByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return invoiceRepository.findAll(new PageRequest(page - 1, size, new Sort(Sort.Direction.ASC, "id"))).getContent();
    }

    @RequestMapping(value = {"/count"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page getCount() {
        return new Page(invoiceRepository.count(), Long.parseLong(env.getProperty("result.page.size")));
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public ObjectNode save(@RequestBody InvoiceRepresentation invoices) {
        ObjectNode node = objectMapper.createObjectNode();
        NumberFormat format = new DecimalFormat("######");

        InvoiceNo invoiceNo = new InvoiceNo();
        invoiceNo.setInvoiceNo(invoiceNoRepository.count() + 1);
        invoiceNo.setCreditPeriod(invoices.getCreditPeriod());
        invoiceNo.setDiscount(invoices.getDiscount());
        invoiceNo.setPoCode(invoices.getPoCode());
        invoiceNo.setPoDate(invoices.getPoDate());
        invoiceNo.setPaymentMethod(invoices.getPaymentMethod());
        invoiceNo.setInvoiceDate(new Date());
        invoiceNo.setTotal(invoices.getTotalPrice());
        invoiceNo.setClient(clientRepository.findOneByCode(invoices.getClientCode()));
        invoiceNo.setRep(repRepository.findOne(invoices.getRepId()));
        invoiceNoRepository.save(invoiceNo);

        String invoiceNoStr = format.format(invoiceNo.getInvoiceNo());

        invoices.getInvoices().stream().forEach(invoice -> {
            invoice.setItem(itemRepository.findOneByCode(invoice.getItemCode()));
            invoice.setInvoiceNo(invoiceNo);
            invoiceRepository.save(invoice);
            Stock one = stockRepository.findOne(invoice.getStockId());

            one.setId(null);
            one.setInvoiceNo(invoiceNoStr);
            one.setReturnInvoiceNo(null);
            one.setLocation(null);
            one.setQty(invoice.getQty() * -1);
            stockRepository.save(one);
        });

        node.put("invoiceNo", invoiceNoStr);
        return node;
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void update(@RequestBody Invoice item) {
        invoiceRepository.save(item);
    }

    @RequestMapping(value = {"/delete/{id}"}, method = {RequestMethod.DELETE}, produces = {"application/json"})
    @Timed
    public void delete(@PathVariable String id) {
        invoiceRepository.delete(Integer.valueOf(id));
    }

}