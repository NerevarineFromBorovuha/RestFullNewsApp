package by.home.fullRestApp.service;

import by.home.fullRestApp.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface NewsService {

    News findNewsById(Long id);

    Page<News> getListNews(int offset, int limit);

    News createNews(News theNews);

    News updateNews(News theNews, Long id);

    void deleteNews(Long theId);

    List<News> findNewsByCriteria(Map<String, String> searchParams);


}
