package by.home.fullRestApp.service;

import by.home.fullRestApp.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NewsService {

    Optional<News> findNewsById(Long id);

    Page<News> getListNews(Pageable pageable);

    News saveNews(News theNews);

    void updateNews(News theNews, Long id);

    void deleteNews(Long theId);


}
