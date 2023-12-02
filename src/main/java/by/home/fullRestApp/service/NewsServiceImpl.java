package by.home.fullRestApp.service;

import by.home.fullRestApp.exception.customException.CommonEntityNotFoundException;
import by.home.fullRestApp.exception.customException.SearchEntityByCriteriaException;
import by.home.fullRestApp.model.News;
import by.home.fullRestApp.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;


    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {

        this.newsRepository = newsRepository;
    }

    // Поиск новости по id
    @Override
    @Transactional
    public News findNewsById(Long id) {

        Optional<News> newsData = newsRepository.findById(id);
        News news = newsData.orElseThrow(() -> (
                new CommonEntityNotFoundException("News is not found, news Id=" + id)));
        log.info("News with number {} successfully found", id);

        return news;
    }

    // Поиск новостей по получаемым критериям
    @Override
    @Transactional
    public List<News> findNewsByCriteria(Map<String, String> searchParams) {

        List<News> listNewsAfterSearch = null;

        if (searchParams.containsKey("title")) {
            listNewsAfterSearch = findNewsByTitle(searchParams.get("title"));
        } else if (searchParams.containsKey("text")) {
            listNewsAfterSearch = findNewsByText(searchParams.get("text"));
        }

        if (listNewsAfterSearch == null) {
            throw new SearchEntityByCriteriaException("The search criteria must be title or text");
        }

        return listNewsAfterSearch;
    }


    // Поиск новостей по заголовку

    private List<News> findNewsByTitle(String title) {

        List<News> newsData = newsRepository.findByTitleContaining(title);
        if (newsData.isEmpty()) {
            log.info("News with title {} empty", title);
            throw new CommonEntityNotFoundException(" News is not found with title = " + title);
        }

        log.info("News with title {} successfully found", title);

        return newsData;
    }

    // Поиск новостей по тексту

    private List<News> findNewsByText(String text) {

        List<News> newsData = newsRepository.findByTextContaining(text);
        if (newsData.isEmpty()) {
            log.warn("News with text {} empty", text);
            throw new CommonEntityNotFoundException(" News is not found with text = " + text);
        }

        log.info("News with text {} successfully found", text);

        return newsData;
    }

    // Поиск списка новостей по параметрам страницы
    @Override
    @Transactional
    public Page<News> getListNews(int offset, int limit) {

        Pageable pageable = PageRequest.of(offset, limit);

        return  newsRepository.findAll(pageable);
    }

    // Создание новости
    @Override
    @Transactional
    public News createNews(News theNews) {

        News savedNews = newsRepository.save(theNews);

        log.info("News : {} successfully create", theNews);

        return savedNews;
    }

    //Обновление новости
    @Override
    @Transactional
    public News updateNews(News theNews, Long id) {

        Optional<News> newsData = newsRepository.findById(id);
        if (newsData.isPresent()) {
            News existNews = newsData.get();

            existNews.setTitle(theNews.getTitle());
            existNews.setText(theNews.getText());
            existNews.setUpdatedById(theNews.getUpdatedById());

            News savedNews = newsRepository.save(existNews);
            log.info("News number: {} successfully update", id);

            return savedNews;

        } else {
            log.info("News number: {} isn't exist", id);
            throw new CommonEntityNotFoundException("News is not found for update, news Id=" + id);
        }
    }

    // Удаление новости по id
    @Override
    @Transactional
    public void deleteNews(Long theId) {

        if (!newsRepository.existsById(theId)) {
            log.info("News number : {} isn't exist", theId);
            throw new CommonEntityNotFoundException("News is not found for delete, news Id=" + theId);
        }

        newsRepository.deleteById(theId);
        log.info("News number : {} successfully delete", theId);
    }
}
