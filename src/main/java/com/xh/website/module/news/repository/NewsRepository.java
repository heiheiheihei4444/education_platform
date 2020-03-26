package com.xh.website.module.news.repository;

import com.xh.website.module.news.entity.News;
import com.xh.website.module.news.entity.NewsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xinghao
 * @descreption
 * @date 2018/12/20
 */

public interface NewsRepository extends JpaRepository<News, Integer> {
    Page<News> findByNewsType(NewsType newsType, Pageable pageable);

    List<News> findByNewsTypeOrderByDateDesc(NewsType newsType,Pageable pageable);
}
