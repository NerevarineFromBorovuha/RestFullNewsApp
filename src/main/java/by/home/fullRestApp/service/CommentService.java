package by.home.fullRestApp.service;

import by.home.fullRestApp.model.Comment;
import by.home.fullRestApp.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentService {

    Optional<Comment> findCommentById(Long id);

    //Page<Comment> getListNews(Pageable pageable);

    Comment saveComment(Comment theComment);

    void updateComment(Comment theComment, Long theId);

    void deleteComment(Long theId);


}
