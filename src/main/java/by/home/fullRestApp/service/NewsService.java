package by.home.fullRestApp.service;

import by.home.fullRestApp.model.News;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    Optional<News> findNewsById(Long id);

    List<News> getListNews();

    void saveNews(News theNews);

    void updateNews(News theNews, Long id);

    void deleteNews(Long theId);


}
