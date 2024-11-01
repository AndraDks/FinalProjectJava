package com.bookingApp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    private static final String API_KEY = "1d70c3dd3ec744d98d900ab6d6b33864";
    private static final String HEADLINES_NEWS_URL = "https://newsapi.org/v2/top-headlines?country=";
    private static final String SEARCH_NEWS_URL = "https://newsapi.org/v2/everything?q=";

    private final RestTemplate restTemplate = new RestTemplate();

    public NewsService(RestTemplate restTemplate) {
    }

    //get country headlines
    public List<Object> getCountryHeadlines(String countryName, int page, int pageSize) {
        String url = HEADLINES_NEWS_URL + countryName + "&language=en&page=" + page + "&pageSize=" + pageSize + "&apiKey=" + API_KEY;
        return fetchNews(url);
    }

    //get specific news
    public List<Object> searchNews(String query, int page, int pageSize) {
        String url = SEARCH_NEWS_URL + query + "&language=en&page=" + page + "&pageSize=" + pageSize + "&apiKey=" + API_KEY;
        return fetchNews(url);
    }

    //fetch 4news
    private List<Object> fetchNews(String url) {
        var response = restTemplate.getForObject(url, Object.class);
        return (List<Object>) ((Map<String, Object>) response).get("articles");
    }

}

