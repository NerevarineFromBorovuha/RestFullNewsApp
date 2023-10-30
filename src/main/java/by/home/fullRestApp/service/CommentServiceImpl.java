package by.home.fullRestApp.service;

import by.home.fullRestApp.model.Comment;
import by.home.fullRestApp.model.News;
import by.home.fullRestApp.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public Optional<Comment> findCommentById(Long idComment) {

        Optional<Comment> commentData = commentRepository.findById(idComment);
        log.info("Comment with number {} successfully found", idComment);
        return commentData;
    }

    @Transactional
    @Override
    public Comment saveComment(Comment theComment) {
        return null;
    }

    @Transactional
    @Override
    public void updateComment(Comment theComment, Long theId) {
        Optional<Comment> commentData = commentRepository.findById(theId);
        if (commentData.isPresent()) {

            Comment existComment = commentData.get();
            existComment.setText(theComment.getText());
            existComment.setInsertedById(theComment.getInsertedById());

            commentRepository.save(existComment);

            log.info("Comment number: {} successfully update", theId);
        } else {
            log.info("Comment number: {} isn't exist", theId);
        }
    }

    @Transactional
    @Override
    public void deleteComment(Long theId) {
        commentRepository.deleteById(theId);
        log.info("Comment number : {} successfully delete", theId);
    }




}
