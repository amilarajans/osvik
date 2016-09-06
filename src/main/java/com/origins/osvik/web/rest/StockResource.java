package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.Stock;
import com.origins.osvik.dto.StockRepresentation;
import com.origins.osvik.repository.StockRepository;
import com.origins.osvik.repository.UnitRepository;
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
@RequestMapping(value = "api/v1/stock")
public class StockResource {
    private final Logger log = LoggerFactory.getLogger(StockResource.class);

    @Autowired
    private Environment env;
    @Qualifier("unitRepository")
    @Autowired
    private UnitRepository unitRepository;
    @Qualifier("stockRepository")
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page<StockRepresentation> getAllByPage(@RequestParam("page") Integer page) {
        return stockRepository.findAllByPage(new PageRequest(page - 1, Integer.parseInt(env.getProperty("result.page.size")), new Sort(Sort.Direction.ASC, "id")));
    }

    @RequestMapping(value = {"/search"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page<StockRepresentation> getSearchAllByPage(@RequestParam("page") Integer page, @RequestParam("q") String query, @RequestParam("mc") String mCat, @RequestParam("sc") String sCat, @RequestParam("s") String supplier) {
        query = (query == null || query.isEmpty()) ? "*" : query;
        return stockRepository.getSearchAllByPage(query.replace("*", "%"), mCat.replace("*", "%"), sCat.replace("*", "%"), supplier.replace("*", "%"), new PageRequest(page - 1, Integer.parseInt(env.getProperty("result.page.size")), new Sort(Sort.Direction.ASC, "id")));
    }

    @RequestMapping(value = {"/allStock"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<StockRepresentation> getAllStock() {
        return stockRepository.findAllStock();
    }

    @RequestMapping(value = {"/stockByItem"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public ObjectNode getStockByItem(@RequestParam("code") String code) {
        ObjectNode node = objectMapper.createObjectNode();
        Double qty = stockRepository.getStockByItem(code);
        node.put("qty", qty == null ? 0 : qty);
        return node;
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void save(@RequestBody Stock item) {
        item.setUnit(unitRepository.findOneByName(item.getUnitName()));
        stockRepository.save(item);
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void update(@RequestBody Stock item) {
        stockRepository.save(item);
    }

    @RequestMapping(value = {"/delete/{id}"}, method = {RequestMethod.DELETE}, produces = {"application/json"})
    @Timed
    public void delete(@PathVariable String id) {
        stockRepository.delete(Integer.valueOf(id));
    }

}