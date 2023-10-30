package by.home.fullRestApp.rest;

import by.home.fullRestApp.model.News;
import by.home.fullRestApp.service.NewsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getNewsById(@PathVariable long newsId) throws Exception {

        // обработка ошибок
        News oneNews = newsService.findNewsById(newsId).orElseThrow(() -> new Exception());
        return new ResponseEntity<>(oneNews, HttpStatus.OK);
    }

    @GetMapping("/news/{newsId}/comment/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable long newsId, @PathVariable long commentId ) throws Exception {

        // обработка ошибок
        News oneNews = newsService.findNewsById(newsId).orElseThrow(() -> new Exception());
        return new ResponseEntity<>(oneNews, HttpStatus.OK);
    }

    private static final String TITLE_PARAMETER = "title";
    private static final String TEXT_PARAMETER = "text";

    // Отправка списка новостей по различным критериям поиска. Критериями поиска выбраны title  и text.
    @GetMapping("/news/search")
    public ResponseEntity<?> getListNewsByParameter(@RequestParam Map<String, String> searchParams) throws Exception {

        // Проверка на присутствие входящих параметров
        if (searchParams.isEmpty()) {
            return new ResponseEntity<>("Request parameters is empty", HttpStatus.BAD_REQUEST);
        }

        // Поиск по заголовку (title)
        if (searchParams.containsKey(TITLE_PARAMETER)) {
            List<News> newsList = newsService.findNewsByTitle(searchParams.get(TITLE_PARAMETER));
            if (newsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(newsList, HttpStatus.OK);
        }

        // Поиск по содержанию (text)
        if (searchParams.containsKey(TEXT_PARAMETER)) {
            List<News> newsList = newsService.findNewsByText(searchParams.get(TEXT_PARAMETER));
            if (newsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(newsList, HttpStatus.OK);
        }

        // Если параметр входящий есть, но он не соответствует критериям поиска
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // Отправка списка новостей
    @GetMapping("/news")
    public ResponseEntity<?> getAllNews(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value ="limit", defaultValue = "5") Integer limit) throws Exception {

        // обработка ошибок
        Page<News> page = newsService.getListNews(PageRequest.of(offset,limit));
        return new ResponseEntity<>(page, HttpStatus.OK);

    }

    //  Создание новой новости
    @PostMapping("/news")
    public ResponseEntity<?> createNews(@RequestBody News news) {

        //верификация данных
        // обработка ошибок
        News savedNews = newsService.saveNews(news);
        return new ResponseEntity<>(savedNews, HttpStatus.CREATED);
    }

    // Обновление новости
    @PutMapping("/news/{newsId}")
    public ResponseEntity<?> updateNews(@RequestBody News news, @PathVariable long newsId) {

        //верификация данных
        // обработка ошибок
        newsService.updateNews(news, newsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Удаление одной новости
    @DeleteMapping("news/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable long newsId) {

        // обработка ошибок
        newsService.deleteNews(newsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
