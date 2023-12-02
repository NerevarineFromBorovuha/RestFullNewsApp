package by.home.fullRestApp.service;

import by.home.fullRestApp.model.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {

    Comment findCommentById(Long id);

    Comment createComment(long idNews, Comment theComment);

    Comment updateComment(Comment theComment, Long theId);

    void deleteComment(Long theId);

    Page<Comment> getListCommentsByNewsId(long newsId, int offset, int limit);


}
