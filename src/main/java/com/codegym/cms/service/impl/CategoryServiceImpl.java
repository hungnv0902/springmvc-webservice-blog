package com.codegym.cms.service.impl;

import com.codegym.cms.model.Category;
import com.codegym.cms.repository.CategoryRepository;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryService;
    @Override
    public Iterable<Category> findAll() {
        return categoryService.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryService.findOne(id);
    }

    @Override
    public void save(Category category) {
        categoryService.save(category);
    }

    @Override
    public void remove(Long id) {
categoryService.delete(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryService.findByName(name);
    }


}
