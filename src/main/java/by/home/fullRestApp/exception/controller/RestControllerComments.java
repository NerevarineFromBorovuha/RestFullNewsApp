package by.home.fullRestApp.exception.controller;


import by.home.fullRestApp.model.Comment;
import by.home.fullRestApp.service.CommentServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
@RequestMapping("/api")
public class RestControllerComments {

    private final CommentServiceImpl commentService;

    @Autowired
    public RestControllerComments(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    // Отправка одного комментария по полученному id
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable long commentId) {

        Comment oneComment = commentService.findCommentById(commentId);

        return new ResponseEntity<>(oneComment, HttpStatus.OK);
    }

    // Отправка списка комментариев по полученному id новости
    @GetMapping("/news/{newsId}/comments")
    public ResponseEntity<Page<Comment>> getCommentById(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit,
            @PathVariable long newsId) {

        Page<Comment> page = commentService.getListCommentsByNewsId(newsId, offset, limit);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //  Создание нового комментария под определенной новостью
    @PostMapping("/news/{newsId}/comments")
    public ResponseEntity<?> createComment(@RequestBody @Valid Comment comment, @PathVariable long newsId) {

        Comment createComment = commentService.createComment(newsId, comment);

        return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }

    // Обновление комментария по Id
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody @Valid Comment comment, @PathVariable long commentId) {

        Comment updatedComment = commentService.updateComment(comment, commentId);

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // Удаление комментария по Id
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteNews(@PathVariable long commentId) {

        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
