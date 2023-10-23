package by.home.fullRestApp.rest;

import by.home.fullRestApp.model.News;
import by.home.fullRestApp.service.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestControllerNews {

    private final NewsServiceImpl newsService;

    @Autowired
    public RestControllerNews(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/{newsId}")
    public News getNewsById(@PathVariable long newsId) throws Exception {

        // обработка ошибок

        News oneNews = newsService.findNewsById(newsId).orElseThrow(() -> new Exception());
        return oneNews;
    }

    @GetMapping("/news")
    public List<News> getListNews() throws Exception {

        // обработка ошибок
        List<News> listAllNews = newsService.getListNews();

        return listAllNews;
    }


    @PostMapping("/news")
    public ResponseEntity<?> createNews(@RequestBody News news) {

        //верификация данных
        // обработка ошибок

        newsService.saveNews(news);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/news/{newsId}")
    public ResponseEntity<?> updateNews(@RequestBody News news, @PathVariable long newsId) {

        //верификация данных
        // обработка ошибок

        newsService.updateNews(news, newsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("news/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable long newsId) {

        // обработка ошибок

        newsService.deleteNews(newsId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
