package com.codegym.cms.controller;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import com.codegym.cms.service.BlogService;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;


    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/create-blog")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog",new Blog());
        return modelAndView;
    }

    @PostMapping("/create-blog")
    public ModelAndView createBlog(@ModelAttribute Blog blog) {
        blogService.save(blog);

        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("message", "New blog created successfully");
        return modelAndView;
    }

    @GetMapping("/blogs")
    public ModelAndView listCustomers(@PageableDefault(size = 3) Pageable pageable){
        Page<Blog> blogs =blogService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/blog/list");
        modelAndView.addObject("blogs",blogs);
        return modelAndView;
    }

    @GetMapping("/edit-blog/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Blog blog = blogService.findById(id);
        if(blog != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/edit");
            modelAndView.addObject("blog",blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("blog/error");
            return modelAndView;
        }
    }

    @PostMapping("/edit-blog")
    public ModelAndView updateBlog(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        modelAndView.addObject("blog",blog);
        modelAndView.addObject("message","edit successfully");
        return modelAndView;
    }

    @GetMapping("/delete-blog/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        Blog blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/blog/delete");
        modelAndView.addObject("blog",blog);
        return modelAndView;
    }

    @PostMapping("/delete-blog")
    public ModelAndView deleteBlog(@ModelAttribute Blog blog) {
        blogService.remove(blog.getId());
        ModelAndView modelAndView = new ModelAndView("/blog/delete");
        modelAndView.addObject("blog",new Blog());
        return modelAndView;
    }

    @GetMapping("/search-blog")
    public ModelAndView searchBlog(@RequestParam("category") String category,Pageable pageable) {
        Category category1 = categoryService.findByName(category);
        Page<Blog> blogs = blogService.findAllByCategory(category1, pageable);
        ModelAndView modelAndView = new ModelAndView("/blog/search");
        modelAndView.addObject("blogs",blogs);
        return modelAndView;
    }

    @GetMapping("/view-blog/{id}")
    public ModelAndView viewBlog(@PathVariable("id") Long id) {
        Blog blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/blog/view");
        modelAndView.addObject("blog",blog);
        return modelAndView;
    }


}
