package by.home.fullRestApp.repository;

import by.home.fullRestApp.model.Comment;
import by.home.fullRestApp.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


}
