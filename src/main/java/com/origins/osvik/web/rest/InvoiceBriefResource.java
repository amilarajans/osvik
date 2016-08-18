package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.*;
import com.origins.osvik.repository.InvoiceNoRepository;
import com.origins.osvik.repository.InvoiceRepository;
import com.origins.osvik.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@RestController
@RequestMapping(value = "api/v1/invoiceBrief")
public class InvoiceBriefResource {
    private final Logger log = LoggerFactory.getLogger(InvoiceBriefResource.class);

    @Autowired
    private Environment env;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceNoRepository invoiceNoRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page<Invoice> getAllByPage(@RequestParam("page") Integer page, @RequestParam(value = "clientCode", required = false) String clientCode, @RequestParam(value = "repCode", required = false) String repCode, @RequestParam(value = "paymentMethod", required = false) String paymentMethod, @RequestParam(value = "invoiceNo", required = false) Long invoiceNo) {
        clientCode = clientCode.replace("*", "%");
        repCode = repCode.replace("*", "%");
        if (!paymentMethod.equals("0")) {
            return invoiceRepository.findAllByCriteria(clientCode, repCode, paymentMethod, invoiceNo, new PageRequest(page - 1, Integer.parseInt(env.getProperty("result.page.size")), new Sort(Sort.Direction.ASC, "id")));
        } else {
            return invoiceRepository.findAllByCriteria(clientCode, repCode, invoiceNo, new PageRequest(page - 1, Integer.parseInt(env.getProperty("result.page.size")), new Sort(Sort.Direction.ASC, "id")));
        }
    }

    @RequestMapping(value = {"/invoiceDetails"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public ObjectNode getInvoiceDetails(@RequestParam(value = "invoiceNo", required = false) Long invoiceNo) {
        Double totalQty = 0d;
        InvoiceNo invoice = invoiceNoRepository.findInvoiceNoByInvoiceNo(invoiceNo);

        ArrayNode arrayNode = objectMapper.createArrayNode();
        ObjectNode client = objectMapper.createObjectNode();

        Client invoiceClient = invoice.getClient();

        client.put("name", invoiceClient.getName());
        client.put("address", invoiceClient.getAddress());
        client.put("tel", invoiceClient.getTel());

        for (Invoice item : invoice.getInvoices()) {

            totalQty += item.getQty();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("qty", item.getQty());
            objectNode.put("unitPrice", item.getUnitPrice());
            Item invoiceItem = item.getItem();
            objectNode.put("itemCode", invoiceItem.getCode());
            objectNode.put("desc", invoiceItem.getDescription());
            objectNode.put("name", invoiceItem.getName());

            Stock stock = stockRepository.findOneByInvoiceNoAndItemCode(invoiceNo.toString(), invoiceItem.getCode(), item.getUnitPrice());

            objectNode.put("doe", stock.getDoe().getTime());
            objectNode.put("lotNo", stock.getLotNo());

            arrayNode.add(objectNode);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("discount", invoice.getDiscount());
        objectNode.put("totalPrice", invoice.getTotal());
        objectNode.put("totalQty", totalQty);
        objectNode.put("poCode", invoice.getPoCode());
        objectNode.put("poDate", invoice.getPoDate().getTime());
        objectNode.set("invoices", arrayNode);
        objectNode.set("client", client);

        return objectNode;
    }

}