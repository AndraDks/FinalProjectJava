package com.bookingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bookingApp.service.NewsService;

import java.util.List;


@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;


    @GetMapping("/news")
    public String newsPage(Model model) {
        model.addAttribute("pageHeader", "Stay Updated with the Latest News");
        return "news";
    }

    @GetMapping("/news-check")
    public String newsCheck(@RequestParam(required = false) String query,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int pageSize,
                            Model model) {
        List<Object> news;
        if (query != null && !query.isEmpty()) {
            news = newsService.searchNews(query, page, pageSize);
            model.addAttribute("query", query);
        } else {
            news = newsService.getCountryHeadlines("us", page, pageSize);
        }
        model.addAttribute("searchResults", news);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        return "news_check";
    }

}
