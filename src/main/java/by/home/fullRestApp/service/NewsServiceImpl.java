package by.home.fullRestApp.service;

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
import java.util.Optional;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;


    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {

        this.newsRepository = newsRepository;
    }


    @Override
    @Transactional
    public Optional<News> findNewsById(Long id) {
        Optional<News> newsData = newsRepository.findById(id);
        log.info("News with number {} successfully found", id);
        return newsData;

    }

    // Поиск новостей по заголовку
    @Transactional
    public List<News> findNewsByTitle(String title) {

        List<News> newsData = newsRepository.findByTitleContaining(title);
        if (newsData.isEmpty()) {
            log.warn("News with title {} empty", title);
        } else {
            log.info("News with title {} successfully found", title);
        }
        return newsData;

    }

    // Поиск новостей по тексту
    @Transactional
    public List<News> findNewsByText(String text) {

        List<News> newsData = newsRepository.findByTextContaining(text);
        if (newsData.isEmpty()) {
            log.warn("News with text {} empty", text);
        } else {
            log.info("News with text {} successfully found", text);
        }
        return newsData;

    }

    @Override
    @Transactional
    public Page<News> getListNews(Pageable pageable) {

        Page<News>  testPage= newsRepository.findAll(pageable);

//        List<News> listNews = newsRepository.findAll();
//        if (!listNews.isEmpty()) {
//            log.info("List News successfully found");
//        } else {
//            log.info("List News not found");
//        }
        return testPage;


    }

    @Override
    @Transactional
    public News saveNews(News theNews) {

        News savedNews = newsRepository.save(theNews);
        log.info("News : {} successfully create", theNews);
        return savedNews;

    }

    @Override
    @Transactional
    public void updateNews(News theNews, Long id) {

        Optional<News> newsData = newsRepository.findById(id);
        if (newsData.isPresent()) {
            News existNews = newsData.get();
            existNews.setTitle(theNews.getTitle());
            existNews.setText(theNews.getText());
            existNews.setUpdatedById(theNews.getUpdatedById());
            newsRepository.save(existNews);
            log.info("News number: {} successfully update", id);
        } else {
            log.info("News number: {} isn't exist", id);
        }

    }

    @Override
    @Transactional
    public void deleteNews(Long theId) {
        newsRepository.deleteById(theId);
        log.info("News number : {} successfully delete", theId);

    }
}
