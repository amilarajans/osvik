package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.Category;
import com.origins.osvik.domain.SubCategory;
import com.origins.osvik.dto.Page;
import com.origins.osvik.repository.CategoryRepository;
import com.origins.osvik.repository.SubCategoryRepository;
import com.origins.osvik.web.rest.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@RestController
@RequestMapping(value = "api/v1/category")
public class CategoryResource {
    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Autowired
    private Environment env;
    @Qualifier("categoryRepository")
    @Autowired
    private CategoryRepository categoryRepository;
    @Qualifier("subCategoryRepository")
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @RequestMapping(value = {"/sub/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<SubCategory> getAllSubCategory() {
        return subCategoryRepository.findAll();
    }

    @RequestMapping(value = {"/count"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page getCount() {
        return new Page(categoryRepository.count(), Long.parseLong(env.getProperty("result.page.size")));
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void saveCategory(@RequestBody ObjectNode actionBody) {
        String categoryName = actionBody.get("name").toString().replace("\"", "");
        if (categoryRepository.findOneByName(categoryName) == null) {
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            categoryRepository.save(newCategory);
        } else {
            throw new ConflictException("Category already exist with name " + categoryName);
        }
    }

    @RequestMapping(value = {"/sub/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void saveSubCategory(@RequestBody ObjectNode actionBody) {
        String subCategoryName = actionBody.get("name").toString().replace("\"", "");
        if (subCategoryRepository.findOneByName(subCategoryName) == null) {
            SubCategory newSubCategory = new SubCategory();
            newSubCategory.setCategory(categoryRepository.findOne(Integer.parseInt(actionBody.get("category").toString().replace("\"", ""))));
            newSubCategory.setName(subCategoryName);
            subCategoryRepository.save(newSubCategory);
        } else {
            throw new ConflictException("Sub Category already exist with name " + subCategoryName);
        }
    }
}