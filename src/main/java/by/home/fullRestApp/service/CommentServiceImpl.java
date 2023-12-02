package by.home.fullRestApp.service;

import by.home.fullRestApp.exception.customException.CommonEntityNotFoundException;
import by.home.fullRestApp.model.Comment;
import by.home.fullRestApp.model.News;
import by.home.fullRestApp.repository.CommentsRepository;
import by.home.fullRestApp.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentsRepository commentsRepository;
    private final NewsRepository newsRepository;

    @Autowired
    public CommentServiceImpl(CommentsRepository commentsRepository, NewsRepository newsRepository) {
        this.commentsRepository = commentsRepository;
        this.newsRepository = newsRepository;
    }

    //Возвращает комментарий по заданному id
    @Transactional
    @Override
    public Comment findCommentById(Long idComment) {

        Optional<Comment> commentData = commentsRepository.findById(idComment);
        Comment comment = commentData.orElseThrow(() -> (
                new CommonEntityNotFoundException("Comment is not found, comment id = " + idComment)));
        log.info("Comment with number {} successfully found", idComment);

        return comment;
    }

    // Возвращает страницу комментариев по заданному id новости +
    @Transactional
    @Override
    public Page<Comment> getListCommentsByNewsId(long newsId, int offset, int limit) {

        Pageable pageable = PageRequest.of(offset, limit);

        return commentsRepository.findByNewsId(pageable, newsId);
    }

    //Создает комментарий к заданной новости +
    @Transactional
    @Override
    public Comment createComment(long newsId, Comment theComment) {

        News existNews = newsRepository.findById(newsId).orElseThrow(() -> (
                new CommonEntityNotFoundException("News is not found, news id = " + newsId)));
        theComment.setNews(existNews);

        Comment savedComment = commentsRepository.save(theComment);
        log.info("Comment : {} successfully create", theComment);

        return savedComment;
    }

    // Обновляет комментарий по заданному id комментария +
    @Transactional
    @Override
    public Comment updateComment(Comment theComment, Long theId) {

        Optional<Comment> commentData = commentsRepository.findById(theId);
        if (commentData.isPresent()) {

            Comment existComment = commentData.get();
            existComment.setText(theComment.getText());
            existComment.setInsertedById(theComment.getInsertedById());
            Comment updatedComment = commentsRepository.save(existComment);
            log.info("Comment number: {} successfully update", theId);

            return updatedComment;

        } else {
            log.info("Comment number: {} isn't exist", theId);
            throw new CommonEntityNotFoundException("Comment is not found for update, news Id=" + theId);

        }
    }

    // Удаление новости по id
    @Transactional
    @Override
    public void deleteComment(Long theId) {

        if (!commentsRepository.existsById(theId)) {
            log.info("Comment number : {} isn't exist", theId);
            throw new CommonEntityNotFoundException("Comment is not found for delete, comment Id=" + theId);
        }

        commentsRepository.deleteById(theId);
        log.info("Comment number : {} successfully delete", theId);
    }

}
