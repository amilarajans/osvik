package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.origins.osvik.domain.Bank;
import com.origins.osvik.dto.Page;
import com.origins.osvik.repository.BankRepository;
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
@RequestMapping(value = "api/v1/bank")
public class BankResource {
    private final Logger log = LoggerFactory.getLogger(BankResource.class);

    @Autowired
    private Environment env;
    @Qualifier("bankRepository")
    @Autowired
    private BankRepository bankRepository;


    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Bank> getAllByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return bankRepository.findAll(new PageRequest(page - 1, size, new Sort(Sort.Direction.ASC, "id"))).getContent();
    }

    @RequestMapping(value = {"/count"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page getCount() {
        return new Page(bankRepository.count(), Long.parseLong(env.getProperty("result.page.size")));
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void save(@RequestBody Bank bank) {
        bankRepository.save(bank);
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void update(@RequestBody Bank bank) {
        bankRepository.save(bank);
    }

    @RequestMapping(value = {"/delete/{id}"}, method = {RequestMethod.DELETE}, produces = {"application/json"})
    @Timed
    public void delete(@PathVariable String id) {
        bankRepository.delete(Integer.valueOf(id));
    }

}