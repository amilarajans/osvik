package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.Category;
import com.origins.osvik.domain.SubCategory;
import com.origins.osvik.repository.CategoryRepository;
import com.origins.osvik.repository.SubCategoryRepository;
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
    public Page<Category> getAllCategory(@RequestParam("name") String name, @RequestParam("page") Integer page) {
        name = name == null ? "%" : name.replace("*", "%");
        return categoryRepository.findCategoryByName(name, new PageRequest(page - 1, Integer.parseInt(env.getProperty("result.page.size")), new Sort(Sort.Direction.ASC, "id")));
    }

    @RequestMapping(value = {"/allCategories"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @RequestMapping(value = {"/sub/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<SubCategory> getAllSubCategory() {
        return subCategoryRepository.findAll();
    }

    @RequestMapping(value = {"/sub/allByCategory"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<SubCategory> getAllSubCategoryByCategory(@RequestParam("id") Integer id) {
        return subCategoryRepository.findAllByCategory(id);
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

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void updateCategory(@RequestBody Category category) {
        categoryRepository.save(category);
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

    @RequestMapping(value = {"/sub/update"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void updateSubCategory(@RequestBody SubCategory subCategory) {
        System.out.println(subCategory);
        Category category = categoryRepository.findOne(subCategory.getCategoryId());
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);
    }

    @RequestMapping(value = {"/sub/delete/{id}"}, method = {RequestMethod.DELETE}, produces = {"application/json"})
    @Timed
    public void delete(@PathVariable String id) {
        subCategoryRepository.delete(Integer.valueOf(id));
    }
}