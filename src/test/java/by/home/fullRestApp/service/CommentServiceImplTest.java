package by.home.fullRestApp.service;

import by.home.fullRestApp.exception.customException.CommonEntityNotFoundException;
import by.home.fullRestApp.model.Comment;
import by.home.fullRestApp.model.News;
import by.home.fullRestApp.repository.CommentsRepository;
import by.home.fullRestApp.repository.NewsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentsRepository commentsRepository;
    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void shouldFindCommentByIdOrThrowException() {

        long numberExistComment = 1L;
        long numberNotExistComment = 2L;

        Comment oneComment = Comment.builder()
                .text("text comment").insertedById(1).build();

        Mockito.when(commentsRepository.findById(numberExistComment)).thenReturn(Optional.ofNullable(oneComment));
        Mockito.when((commentsRepository.findById(numberNotExistComment))).thenReturn(Optional.empty());

        Comment existingComment = commentService.findCommentById(numberExistComment);

        Assertions.assertThat(existingComment).isNotNull();
        Assertions.assertThatThrownBy(() -> commentService.findCommentById(numberNotExistComment))
                .isInstanceOf(CommonEntityNotFoundException.class);

    }

    @Test
    public void shouldReturnCommentPageByNewsId() {

        Page<Comment> dataComment = Mockito.mock(Page.class);
        Pageable pageable = PageRequest.of(0, 3);

        Mockito.when(commentsRepository.findByNewsId(pageable, 1L)).thenReturn(dataComment);

        Page<Comment> commentPage = commentService.getListCommentsByNewsId(1, 0, 3);

        Assertions.assertThat(commentPage).isNotNull();

    }

    @Test
    public void shouldFindNewsAndCreateComment() {

        Comment oneComment = Comment.builder().text("text comment")
                .insertedById(1).id(1).build();
        News oneNews = News.builder().title("Title")
                .text("Text").insertedById(1).build();

        Mockito.when(newsRepository.findById((long) oneComment.getId()))
                .thenReturn(Optional.of(oneNews));

        Mockito.when(commentsRepository.save(Mockito.any(Comment.class)))
                .thenReturn(oneComment);

        Comment createdComment = commentService.createComment(oneComment.getId(), oneComment);

        Assertions.assertThat(createdComment).isNotNull().hasFieldOrPropertyWithValue("text", "text comment");

    }

    @Test
    public void shouldUpdateCommentByIdAndReturnCommentOrThrowException() {

        long numberNotExistingComment = 2L;

        Comment oneComment = Comment.builder().text("text comment")
                .insertedById(1).id(1).build();

        Mockito.when(commentsRepository.findById((long) oneComment.getId()))
                .thenReturn(Optional.of(oneComment));

        Mockito.when(commentsRepository.findById(numberNotExistingComment))
                .thenReturn(Optional.empty());

        Mockito.when(commentsRepository.save(Mockito.any(Comment.class)))
                .thenReturn(oneComment);

        Comment updatedComment = commentService.updateComment(oneComment, (long) oneComment.getId());

        Assertions.assertThat(updatedComment).isNotNull();
        Assertions.assertThatThrownBy(() -> commentService.updateComment(oneComment, numberNotExistingComment))
                .isInstanceOf(CommonEntityNotFoundException.class);


    }

    @Test
    public void shouldDeleteCommentOrThrowException() {

        long numberExistingComment = 1L;
        long numberNotExistingComment = 2L;

        Mockito.when(commentsRepository.existsById(numberExistingComment))
                .thenReturn(true);
        Mockito.when(commentsRepository.existsById(numberNotExistingComment))
                .thenReturn(false);

        assertAll(() -> commentService.deleteComment(numberExistingComment));
        Assertions.assertThatThrownBy(() -> commentService.deleteComment(numberNotExistingComment))
                .isInstanceOf(CommonEntityNotFoundException.class);
    }

}
