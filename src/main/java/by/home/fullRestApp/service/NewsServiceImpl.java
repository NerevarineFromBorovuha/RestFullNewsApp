package by.home.fullRestApp.service;

import by.home.fullRestApp.model.News;
import by.home.fullRestApp.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
    public List<News> getListNews() {

        List<News> listNews = newsRepository.findAll();
        if (!listNews.isEmpty()) {
            log.info("List News successfully found");
        }else {
            log.info("List News not found");
        }
        return listNews;


    }

    @Override
    @Transactional
    public void saveNews(News theNews) {
        newsRepository.save(theNews);
        log.info("News : {} successfully create", theNews);

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
