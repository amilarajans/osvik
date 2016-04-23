package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.origins.osvik.domain.Supplier;
import com.origins.osvik.dto.Page;
import com.origins.osvik.repository.SupplierRepository;
import com.origins.osvik.web.rest.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@RestController
@RequestMapping(value = "api/v1/supplier")
public class SupplierResource {
    private final Logger log = LoggerFactory.getLogger(SupplierResource.class);
    @Qualifier("supplierRepository")
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private Environment env;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Supplier> getAllByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return supplierRepository.findAll(new PageRequest(page - 1, size, new Sort(Sort.Direction.ASC, "id"))).getContent();
    }

    @RequestMapping(value = {"/allSupplier"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @RequestMapping(value = {"/count"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page getCount() {
        return new Page(supplierRepository.count(), Long.parseLong(env.getProperty("result.page.size")));
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void save(@RequestBody Supplier supplier) {
        if (supplierRepository.findOneByCode(supplier.getCode()) == null) {
            supplierRepository.save(supplier);
        } else {
            throw new ConflictException("Supplier already exist with code " + supplier.getCode());
        }
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void update(@RequestBody Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @RequestMapping(value = {"/delete/{id}"}, method = {RequestMethod.DELETE}, produces = {"application/json"})
    @Timed
    public void delete(@PathVariable String id) {
        supplierRepository.delete(Integer.valueOf(id));
    }

}