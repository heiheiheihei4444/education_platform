package com.xh.website.module.common.controller;

import com.xh.website.module.news.entity.News;
import com.xh.website.module.news.entity.NewsType;
import com.xh.website.module.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author xinghao
 * @descreption
 * @date 2018/12/28
 */

@Controller
public class CommonController {

    private final
    NewsRepository newsRepository;

    @Autowired
    public CommonController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    //取日期最近的5条新闻放到轮播图
    @GetMapping(value = {"/index",""})
    public String index(Model model){
        Pageable pageable = PageRequest.of(0, 6);
        List<News> newsList = newsRepository.findByNewsTypeOrderByDateDesc(NewsType.NEWS,pageable);
        model.addAttribute("newsList",newsList);
        return "index";
    }

    @GetMapping("/aboutus")
    public String about(){
        return "aboutus";
    }

    @GetMapping("/join")
    public String join(){
        return "join";
    }
}
