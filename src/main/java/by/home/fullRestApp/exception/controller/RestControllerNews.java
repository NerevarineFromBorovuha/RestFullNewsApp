package by.home.fullRestApp.exception.controller;

import by.home.fullRestApp.model.News;
import by.home.fullRestApp.service.NewsServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@Slf4j
@RequestMapping("/api")
public class RestControllerNews {

    private final NewsServiceImpl newsService;

    @Autowired
    public RestControllerNews(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    // Отправка одной новости по полученному id
    @GetMapping("/news/{newsId}")
    public ResponseEntity<?> getNewsById(@PathVariable long newsId) {

        News oneNews = newsService.findNewsById(newsId);

        return new ResponseEntity<>(oneNews, HttpStatus.OK);

    }


    // Отправка списка новостей по различным критериям поиска. Критериями поиска выбраны title  и text.
    @GetMapping("/news/search")
    public ResponseEntity<?> getListNewsByParameter(@RequestParam @NotEmpty Map<String, String> searchParams) {

        List<News> newsListAfterSearch = newsService.findNewsByCriteria(searchParams);

        return new ResponseEntity<>(newsListAfterSearch, HttpStatus.OK);
    }


    // Отправка списка новостей по получаемым параметрам offset и limit
    @GetMapping("/news")
    public ResponseEntity<Page<News>> getAllNews(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit) {

        Page<News> page = newsService.getListNews(offset, limit);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //  Создание новой новости
    @PostMapping("/news")
    public ResponseEntity<News> createNews(@RequestBody @Valid News news) {

        News savedNews = newsService.createNews(news);

        return new ResponseEntity<>(savedNews, HttpStatus.CREATED);
    }

    // Обновление новости
    @PutMapping("/news/{newsId}")
    public ResponseEntity<?> updateNews(@RequestBody @Valid News news, @PathVariable long newsId) {

        News updatedNews = newsService.updateNews(news, newsId);

        return new ResponseEntity<>(updatedNews, HttpStatus.OK);
    }

    // Удаление одной новости
    @DeleteMapping("news/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable long newsId) {

        newsService.deleteNews(newsId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
