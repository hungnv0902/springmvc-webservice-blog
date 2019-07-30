package com.codegym.cms.controller;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import com.codegym.cms.service.BlogService;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class CategoryController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<Iterable<Category>> listCategories(){
        Iterable<Category> categories;
        categories = categoryService.findAll();
        return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
    }



    @PostMapping("/create-category")
    public ResponseEntity<Blog> saveCategory(@RequestBody Category category, UriComponentsBuilder ucBuilder){
        categoryService.save(category);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/category/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<Blog>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/edit-category/{id}")
    public ResponseEntity<Category> showEditForm(@PathVariable("id") Long id, @RequestBody Category category) {
        Category currentCategory = categoryService.findById(id);
        if(currentCategory == null) {
            System.out.println("Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        currentCategory.setName(category.getName());
       currentCategory.setBlogs(category.getBlogs());
       currentCategory.setId(category.getId());

         categoryService.save(currentCategory);
        return new ResponseEntity<Category>(currentCategory, HttpStatus.OK);
    }



}