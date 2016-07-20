package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.origins.osvik.domain.Item;
import com.origins.osvik.dto.ItemRepresentation;
import com.origins.osvik.repository.*;
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
@RequestMapping(value = "api/v1/item")
public class ItemResource {
    private final Logger log = LoggerFactory.getLogger(ItemResource.class);

    @Autowired
    private Environment env;
    @Qualifier("itemRepository")
    @Autowired
    private ItemRepository itemRepository;
    @Qualifier("subCategoryRepository")
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Qualifier("categoryRepository")
    @Autowired
    private CategoryRepository categoryRepository;
    @Qualifier("unitRepository")
    @Autowired
    private UnitRepository unitRepository;
    @Qualifier("supplierRepository")
    @Autowired
    private SupplierRepository supplierRepository;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page<Item> getAllByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return itemRepository.findAllByPage(new PageRequest(page - 1, Integer.parseInt(env.getProperty("result.page.size")), new Sort(Sort.Direction.ASC, "id")));
    }

    @RequestMapping(value = {"/allItems"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Item> getAllByPage() {
        return itemRepository.findAll();
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void save(@RequestBody ItemRepresentation item) {
        if(itemRepository.findOneByCode(item.getCode())==null) {
            Item newItem = new Item();
            newItem.setName(item.getName());
            newItem.setCode(item.getCode());
            newItem.setCategory(categoryRepository.findOne(item.getCategory()));
            newItem.setSubCategory(subCategoryRepository.findOne(item.getSubCategory()));
            newItem.setUnit(unitRepository.findOne(item.getUnit()));
            newItem.setSupplier(supplierRepository.findOne(item.getSupplier()));
            newItem.setCountry(item.getCountry());
            newItem.setDescription(item.getDescription());
            itemRepository.save(newItem);
        } else {
            throw new ConflictException("Item already exist with code " + item.getCode());
        }
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void update(@RequestBody Item item) {
        itemRepository.save(item);
    }

    @RequestMapping(value = {"/delete/{id}"}, method = {RequestMethod.DELETE}, produces = {"application/json"})
    @Timed
    public void delete(@PathVariable String id) {
        itemRepository.delete(Integer.valueOf(id));
    }

}