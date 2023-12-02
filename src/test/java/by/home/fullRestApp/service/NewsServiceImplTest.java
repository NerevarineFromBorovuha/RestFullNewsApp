package by.home.fullRestApp.service;

import by.home.fullRestApp.exception.customException.CommonEntityNotFoundException;
import by.home.fullRestApp.exception.customException.SearchEntityByCriteriaException;
import by.home.fullRestApp.model.News;
import by.home.fullRestApp.repository.NewsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsServiceImpl newsService;

    @Test
    public void shouldCreateOneNewsAndReturnNews() {

        News firstNews = News.builder()
                .title("first title")
                .text("first text")
                .insertedById(1).build();

        Mockito.when(newsRepository.save(Mockito.any(News.class))).thenReturn(firstNews);

        News savedNews = newsService.createNews(firstNews);

        Assertions.assertThat(savedNews).isNotNull();

    }

    @Test
    public void shouldFindNewsByIdOrThrowException() {

        long numberExistingComment = 1L;
        long numberNotExistingComment = 2L;

        News firstNews = News.builder()
                .title("first title")
                .text("first text")
                .insertedById(1).build();

        Mockito.when(newsRepository.findById(numberExistingComment)).thenReturn(Optional.ofNullable(firstNews));
        Mockito.when(newsRepository.findById(numberNotExistingComment)).thenReturn(Optional.empty());

        News existingNews = newsService.findNewsById(numberExistingComment);

        Assertions.assertThat(existingNews).isNotNull();
        Assertions.assertThatThrownBy(() -> newsService.findNewsById(numberNotExistingComment))
                .isInstanceOf(CommonEntityNotFoundException.class);

    }

    @Test
    public void shouldReturnPage() {

        Page<News> dataNews = Mockito.mock(Page.class);

        Mockito.when(newsRepository.findAll(Mockito.any(Pageable.class))).thenReturn(dataNews);

        Page<News> newsPage = newsService.getListNews(1, 10);

        Assertions.assertThat(newsPage).isNotNull();

    }

    @Test
    public void shouldFindListNewsByCriteriaTitle() {

        News firstNews = News.builder()
                .title("Correct title 1")
                .text("first text")
                .insertedById(1).build();
        News secondNews = News.builder()
                .title("Correct title 2")
                .text("second text")
                .insertedById(2).build();

        List<News> newsList = new ArrayList<>();
        newsList.add(firstNews);
        newsList.add(secondNews);

        Map<String, String> mapWithCriteria = new HashMap<>();
        mapWithCriteria.put("title", "Correct");

        Mockito.when(newsRepository.findByTitleContaining("Correct")).thenReturn(newsList);
        Mockito.when(newsRepository.findByTitleContaining("Correct")).thenReturn(newsList);

        List<News> listAfterSearching = newsService.findNewsByCriteria(mapWithCriteria);

        Assertions.assertThat(listAfterSearching).isNotEmpty();
        Assertions.assertThat(listAfterSearching).contains(firstNews, secondNews);

    }

    @Test
    public void shouldFindListNewsByCriteriaText() {

        News firstNews = News.builder()
                .title("title 1")
                .text("Correct text 1")
                .insertedById(1).build();
        News secondNews = News.builder()
                .title("title 2")
                .text("Correct text 2")
                .insertedById(2).build();

        List<News> newsList = new ArrayList<>();
        newsList.add(firstNews);
        newsList.add(secondNews);

        Map<String, String> mapWithCriteria = new HashMap<>();
        mapWithCriteria.put("text", "Correct");

        Mockito.when(newsRepository.findByTextContaining("Correct")).thenReturn(newsList);

        List<News> listAfterSearching = newsService.findNewsByCriteria(mapWithCriteria);

        Assertions.assertThat(listAfterSearching).isNotEmpty();
        Assertions.assertThat(listAfterSearching).contains(firstNews, secondNews);

    }

    @Test
    public void shouldThrowExceptionIfCriteriaNotCorrectOrNotMatches() {

        Map<String, String> mapWithIncorrectCriteria = new HashMap<>();
        mapWithIncorrectCriteria.put("incorrect", "incorrect value");

        Map<String, String> mapWithCorrectCriteria = new HashMap<>();
        mapWithCorrectCriteria.put("title", "value");

        List<News> emptyList = new ArrayList<>();

        Mockito.when(newsRepository.findByTitleContaining("value")).thenReturn(emptyList);

        Assertions.assertThatThrownBy(() -> newsService.findNewsByCriteria(mapWithCorrectCriteria))
                .isInstanceOf(CommonEntityNotFoundException.class);

        Assertions.assertThatThrownBy(() -> newsService.findNewsByCriteria(mapWithIncorrectCriteria))
                .isInstanceOf(SearchEntityByCriteriaException.class);

    }


    @Test
    public void shouldUpdateNewsByIdOrThrowException() {

        long numberExistingComment = 1L;
        long numberNotExistingComment = 2L;

        News firstNews = News.builder()
                .title("first title")
                .text("first text")
                .insertedById(1).build();

        Mockito.when(newsRepository.findById(numberExistingComment)).thenReturn(Optional.ofNullable(firstNews));
        Mockito.when(newsRepository.save(Mockito.any(News.class))).thenReturn(firstNews);

        Mockito.when(newsRepository.findById(numberNotExistingComment)).thenReturn(Optional.empty());

        News updatedNews = newsService.updateNews(firstNews, numberExistingComment);

        Assertions.assertThat(updatedNews).isNotNull();
        Assertions.assertThatThrownBy(() -> newsService.updateNews(firstNews, numberNotExistingComment))
                .isInstanceOf(CommonEntityNotFoundException.class);

    }

    @Test
    public void shouldDeleteNewsOrThrowException() {

        long numberExistingComment = 1L;
        long numberNotExistingComment = 2L;

        Mockito.when(newsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(newsRepository.existsById(2L)).thenReturn(false);

        assertAll(() -> newsService.deleteNews(numberExistingComment));
        Assertions.assertThatThrownBy(() -> newsService.deleteNews(numberNotExistingComment))
                .isInstanceOf(CommonEntityNotFoundException.class);


    }


}
