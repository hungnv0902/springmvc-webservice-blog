package com.codegym.cms.controller;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import com.codegym.cms.service.BlogService;
import com.codegym.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;


    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }



    @PostMapping(value = "/create-blog")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Blog " + blog.getName());
        blogService.save(blog);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/blog/{id}").buildAndExpand(blog.getId()).toUri());
        return new ResponseEntity<Blog>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/blogs")
    public ResponseEntity<Page<Blog>> listBlogs(@PageableDefault(size = 3) Pageable pageable){
        Page<Blog> blogs =blogService.findAll(pageable);
        if (blogs == null) {
            return new ResponseEntity<Page<Blog>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Page<Blog>>(blogs, HttpStatus.OK);
    }


    @PutMapping("/edit-blog/{id}")
    public ResponseEntity<Blog> showEditForm(@PathVariable("id") Long id,@RequestBody Blog blog) {
        Blog currentBlog = blogService.findById(id);
        if(currentBlog == null) {
            System.out.println("Blog with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
        currentBlog.setName(blog.getName());
        currentBlog.setCategory(blog.getCategory());
        currentBlog.setId(blog.getId());
        currentBlog.setContent(blog.getContent());

        blogService.save(currentBlog);
        return new ResponseEntity<Blog>(currentBlog, HttpStatus.OK);
    }



    @DeleteMapping("/delete-blog/{id}")
    public ResponseEntity<Blog> showDeleteForm(@PathVariable("id") Long id) {
        Blog blog = blogService.findById(id);
        if(blog == null) {
            System.out.println("Blog with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
        blogService.remove(id);
        return new ResponseEntity<Blog>(blog, HttpStatus.OK);
    }


    @GetMapping("/search-blog/")
    public ResponseEntity<List<Blog>> searchBlog(@RequestParam("category") String category) {
        Category category1 = categoryService.findByName(category);
        if(category1 == null) {
            System.out.println("Blog with category " + category + " not found");
            return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
        }
        List<Blog> blogs = blogService.findAllByCategory(category1);
        return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
    }

    @GetMapping("/view-blog/{id}")
    public ModelAndView viewBlog(@PathVariable("id") Long id) {
        Blog blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/blog/view");
        modelAndView.addObject("blog",blog);
        return modelAndView;
    }


}
