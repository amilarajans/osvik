package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.Invoice;
import com.origins.osvik.domain.ReturnInvoice;
import com.origins.osvik.domain.ReturnInvoiceNo;
import com.origins.osvik.domain.Stock;
import com.origins.osvik.dto.InvoiceNoRepresentation;
import com.origins.osvik.dto.ReturnInvoiceRepresentation;
import com.origins.osvik.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@RestController
@RequestMapping(value = "api/v1/return")
public class ReturnResource {
    private final Logger log = LoggerFactory.getLogger(ReturnResource.class);

    @Autowired
    private Environment env;
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
    @Autowired
    private ReturnInvoiceNoRepository returnInvoiceNoRepository;
    @Autowired
    private ReturnInvoiceRepository returnInvoiceRepository;

    @RequestMapping(value = {"/allInvoices"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<InvoiceNoRepresentation> getAllInvoiceNo() {
        return invoiceNoRepository.findAllInvoice();
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public ObjectNode save(@RequestBody ReturnInvoiceRepresentation returnInvoices) {
        ObjectNode node = objectMapper.createObjectNode();
        NumberFormat format = new DecimalFormat("######");

        ReturnInvoiceNo returnInvoiceNo = new ReturnInvoiceNo();
        returnInvoiceNo.setCauseOfReturn(returnInvoices.getCauseOfReturn());
        returnInvoiceNo.setInvoiceNo(returnInvoices.getInvoiceNo());
        returnInvoiceNo.setReturnDate(returnInvoices.getReturnDate());
        returnInvoiceNo.setReturnInvoiceNo(returnInvoiceNoRepository.count() + 1);
        returnInvoiceNoRepository.save(returnInvoiceNo);

        String invoiceNoStr = format.format(returnInvoiceNo.getReturnInvoiceNo());

        returnInvoices.getReturnItemsList().stream().forEach(invoice -> {
            ReturnInvoice returnInvoice = new ReturnInvoice();
            returnInvoice.setInvoiceNo(returnInvoiceNo);
            returnInvoice.setItem(itemRepository.findOneByCode(invoice.getItemCode()));
            returnInvoice.setQty(invoice.getQty());
            returnInvoice.setUnitPrice(invoice.getUnitPrice());
            returnInvoiceRepository.save(returnInvoice);

            Stock one = stockRepository.findOne(invoice.getStockId());

            one.setId(null);
            one.setInvoiceNo(null);
            one.setReturnInvoiceNo(invoiceNoStr);
            one.setLocation(null);
            one.setQty(invoice.getQty());
            stockRepository.save(one);
        });

        node.put("returnInvoiceNo", invoiceNoStr);
        return node;
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void update(@RequestBody Invoice item) {
        invoiceRepository.save(item);
    }

}