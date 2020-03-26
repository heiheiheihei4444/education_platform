package com.xh.website.module.news.controller;

import com.xh.website.module.news.entity.News;
import com.xh.website.module.news.entity.NewsType;
import com.xh.website.module.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author xinghao
 * @descreption
 * @date 2018/12/20
 */

@Controller
@RequestMapping("/news")
public class NewsController {

    private final
    NewsRepository newsRepository;

    @Autowired
    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("newslist")
    public String getAll( Model model) {
        Pageable newsPageable = PageRequest.of(0, 6,Sort.Direction.DESC,"id");
        Page<News> newsPage = newsRepository.findByNewsType(NewsType.NEWS,newsPageable);

        model.addAttribute("newsPage", newsPage);

        Page<News> notPage = newsRepository.findByNewsType(NewsType.NOTIFICATION,newsPageable);
        model.addAttribute("notPage",notPage);

        return "eventlist";
    }

    @GetMapping("/news")
    public String getNews(@RequestParam int id,Model model) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (!newsOptional.isPresent()) {
            return "error";
        }
        News news = newsOptional.get();
        model.addAttribute("news", news);
        return "news";
    }

    @GetMapping("/notification")
    public String getNot(@RequestParam int id,Model model) {
        Optional<News> notOptional = newsRepository.findById(id);
        if (!notOptional.isPresent()) {
            return "error";
        }
        News notification = notOptional.get();
        model.addAttribute("notification", notification);
        return "notice";
    }

    @ResponseBody
    @GetMapping("/newspage")
    public Page<News> getNewsPage(@RequestParam(required = false , defaultValue = "0") int newsPageNow) {
        Pageable newsPageable = PageRequest.of(newsPageNow, 6,Sort.Direction.DESC,"id");
        Page<News> newsPage = newsRepository.findByNewsType(NewsType.NEWS,newsPageable);
        return newsPage;
    }

    @ResponseBody
    @GetMapping("/notpage")
    public Page<News> getNotPage(@RequestParam(required = false , defaultValue = "0") int notPageNow) {
        Pageable notPageable = PageRequest.of(notPageNow, 6,Sort.Direction.DESC,"id");
        Page<News> notPage = newsRepository.findByNewsType(NewsType.NOTIFICATION,notPageable);
        return notPage;
    }

}
