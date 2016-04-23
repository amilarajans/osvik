package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.Unit;
import com.origins.osvik.dto.Page;
import com.origins.osvik.repository.UnitRepository;
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
@RequestMapping(value = "api/v1/unit")
public class UnitResource {
    private final Logger log = LoggerFactory.getLogger(UnitResource.class);

    @Autowired
    private Environment env;

    @Qualifier("unitRepository")
    @Autowired
    private UnitRepository unitRepository;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Unit> getAllByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return unitRepository.findAll(new PageRequest(page - 1, size, new Sort(Sort.Direction.ASC, "id"))).getContent();
    }

    @RequestMapping(value = {"/allUnit"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Unit> getAll() {
        return unitRepository.findAll();
    }

    @RequestMapping(value = {"/count"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page getCount() {
        return new Page(unitRepository.count(), Long.parseLong(env.getProperty("result.page.size")));
    }

    @RequestMapping(value = {"/fetchByName"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Unit getOneByName(@RequestParam("name") String name) {
        System.out.println(name);
        return unitRepository.findOneByName(name);
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void save(@RequestBody ObjectNode actionBody) {
        String unitName = actionBody.get("name").toString().replace("\"", "");
        if (unitRepository.findOneByName(unitName) == null) {
            Unit unit = new Unit();
            unit.setName(unitName);
            unitRepository.save(unit);
        } else {
            throw new ConflictException("Unit already exist with name " + unitName);
        }
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void update(@RequestBody Unit unit) {
        unitRepository.save(unit);
    }

    @RequestMapping(value = {"/delete/{id}"}, method = {RequestMethod.DELETE}, produces = {"application/json"})
    @Timed
    public void delete(@PathVariable String id) {
        unitRepository.delete(Integer.valueOf(id));
    }


}