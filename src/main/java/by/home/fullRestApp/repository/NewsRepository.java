package by.home.fullRestApp.repository;

import by.home.fullRestApp.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {


    List<News> findByTitleContaining(String title);
    List<News> findByTextContaining(String text);





}
