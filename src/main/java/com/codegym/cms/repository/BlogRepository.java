package com.codegym.cms.repository;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {
    List<Blog> findAllByCategory(Category category);
    Page<Blog> findAll(Pageable pageable);
}
