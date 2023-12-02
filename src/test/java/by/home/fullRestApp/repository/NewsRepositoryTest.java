package by.home.fullRestApp.repository;

import by.home.fullRestApp.model.News;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void shouldSaveOneNewsAndReturnNews() {

        News firstNews = News.builder().title("first title").text("first text").insertedById(1).build();

        News savedNews = newsRepository.save(firstNews);

        Assertions.assertThat(savedNews).isNotNull();
        Assertions.assertThat(savedNews.getId()).isGreaterThan(0);

    }

    @Test
    public void shouldFindNewsByTitle() {

        News firstNews = News.builder()
                .title("first title")
                .text("text")
                .insertedById(1).build();
        News secondNews = News.builder()
                .title("second title")
                .text("text")
                .insertedById(2).build();

        newsRepository.save(firstNews);
        newsRepository.save(secondNews);

        String title = "second";
        List<News> newsList = newsRepository.findByTitleContaining(title);
        Assertions.assertThat(newsList).isNotNull();
        Assertions.assertThat(newsList.size()).isEqualTo(1);

    }

    @Test
    public void shouldFindNewsByText() {

        News firstNews = News.builder()
                .title("title")
                .text("first text")
                .insertedById(1).build();
        News secondNews = News.builder()
                .title("title")
                .text("second text")
                .insertedById(2).build();

        newsRepository.save(firstNews);
        newsRepository.save(secondNews);

        String text = "second";
        List<News> newsList = newsRepository.findByTextContaining(text);
        Assertions.assertThat(newsList).isNotNull();
        Assertions.assertThat(newsList.size()).isEqualTo(1);

    }


    @Test
    public void shouldFindNewsById() {

        News firstNews = News.builder()
                .title("first title")
                .text("first text")
                .insertedById(1).build();

        newsRepository.save(firstNews);

        News newsReturn = newsRepository.findById((long) firstNews.getId()).get();

        Assertions.assertThat(newsReturn).isNotNull();

    }

    @Test
    public void shouldReturnFirstPageWithTwoElements() {

        News firstNews = News.builder()
                .title("first title")
                .text("first text")
                .insertedById(1).build();
        News secondNews = News.builder()
                .title("second title")
                .text("second text")
                .insertedById(2).build();
        News thirdNews = News.builder()
                .title("third title")
                .text("third text")
                .insertedById(3).build();

        newsRepository.save(firstNews);
        newsRepository.save(secondNews);
        newsRepository.save(thirdNews);

        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);

        Page<News> pageNews = newsRepository.findAll(firstPageWithTwoElements);

        Assertions.assertThat(pageNews.getContent())
                .isEqualTo(Arrays.asList(firstNews, secondNews));


    }

    @Test
    public void shouldDeleteOneNewsAndReturnNewsIsEmpty() {

        News firstNews = News.builder()
                .title("first title")
                .text("first text")
                .insertedById(1).build();

        newsRepository.save(firstNews);

        newsRepository.deleteById((long) firstNews.getId());
        Optional<News> returnNews = newsRepository.findById((long) firstNews.getId());

        Assertions.assertThat(returnNews).isEmpty();

    }

    @Test
    public void shouldUpdateOneNewsAndReturnNews() {

        News firstNews = News.builder()
                .title("first title")
                .text("first text")
                .insertedById(1).build();

        newsRepository.save(firstNews);

        News newsReturn = newsRepository.findById((long) firstNews.getId()).get();
        newsReturn.setTitle("new title");
        newsReturn.setText("new text");
        News updatedNews = newsRepository.save(newsReturn);

        Assertions.assertThat(updatedNews.getTitle()).isEqualTo("new title");
        Assertions.assertThat(updatedNews.getText()).isEqualTo("new text");

    }

}
