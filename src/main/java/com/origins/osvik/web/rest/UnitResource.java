package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.Unit;
import com.origins.osvik.repository.UnitRepository;
import com.origins.osvik.web.rest.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
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
    public Page<Unit> getAllByPage(@RequestParam("name") String name, @RequestParam("page") Integer page) {
        name = name == null ? "%" : name.replace("*", "%");
        return unitRepository.findUnitByName(name, new PageRequest(page - 1, Integer.parseInt(env.getProperty("result.page.size")), new Sort(Sort.Direction.ASC, "id")));
    }

    @RequestMapping(value = {"/allUnit"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Unit> getAll() {
        return unitRepository.findAll();
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