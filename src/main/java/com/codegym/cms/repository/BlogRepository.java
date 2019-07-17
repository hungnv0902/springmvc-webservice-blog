package com.codegym.cms.repository;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {
    Page<Blog> findAllByCategory(Category category, Pageable pageable);
    @Query("SELECT e FROM Blog e ORDER BY e.createDate ASC")
    Page<Blog> findAll(Pageable pageable);
}
