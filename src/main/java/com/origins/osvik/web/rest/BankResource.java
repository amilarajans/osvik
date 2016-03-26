package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.origins.osvik.domain.Bank;
import com.origins.osvik.dto.BankRepresentation;
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
    public void save(@RequestBody BankRepresentation bank) {
        Bank newBank=new Bank();
        newBank.setName(bank.getName());
        newBank.setAccNo(bank.getAccNo());
        newBank.setAccType(bank.getAccType());
        newBank.setEmail(bank.getEmail());
        newBank.setTel(bank.getTel());
        newBank.setRemark(bank.getRemark());
        bankRepository.save(newBank);
    }


}