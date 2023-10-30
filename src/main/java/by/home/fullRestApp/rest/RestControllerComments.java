package by.home.fullRestApp.rest;


import by.home.fullRestApp.model.Comment;
import by.home.fullRestApp.model.News;
import by.home.fullRestApp.service.CommentService;
import by.home.fullRestApp.service.CommentServiceImpl;
import by.home.fullRestApp.service.NewsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class RestControllerComments {

    private final CommentServiceImpl commentService;

    @Autowired
    public RestControllerComments(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }


    // Отправка одного комментария по полученному id
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable long commentId) throws Exception {
        // обработка ошибок
        Comment oneComment = commentService.findCommentById(commentId).orElseThrow(() -> new Exception());
        return new ResponseEntity<>(oneComment, HttpStatus.OK);
    }

    // Удаление комментария по Id
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteNews(@PathVariable long commentId) {
        // обработка ошибок
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Обновление комментария по Id
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> saveNews(@RequestBody Comment comment,@PathVariable long commentId) {
        // обработка ошибок


        commentService.updateComment(comment,commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
