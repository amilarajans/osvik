package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.origins.osvik.domain.Rep;
import com.origins.osvik.dto.Page;
import com.origins.osvik.dto.RepRepresentation;
import com.origins.osvik.repository.RepRepository;
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
@RequestMapping(value = "api/v1/rep")
public class RepResource {
    private final Logger log = LoggerFactory.getLogger(RepResource.class);

    @Autowired
    private Environment env;
    @Qualifier("repRepository")
    @Autowired
    private RepRepository repRepository;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Rep> getAllByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return repRepository.findAll(new PageRequest(page - 1, size, new Sort(Sort.Direction.ASC, "id"))).getContent();
    }

    @RequestMapping(value = {"/count"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page getCount() {
        return new Page(repRepository.count(), Long.parseLong(env.getProperty("result.page.size")));
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void save(@RequestBody RepRepresentation rep) {
        if (repRepository.findOneByName(rep.getName()) == null) {
            Rep newRep = new Rep();
            newRep.setName(rep.getName());
            newRep.setEmail(rep.getEmail());
            newRep.setAddress(rep.getAddress());
            newRep.setTel(rep.getTel());
            newRep.setRemark(rep.getRemark());
            repRepository.save(newRep);
        } else {
            throw new ConflictException("Rep already exist with name " + rep.getName());
        }
    }


}