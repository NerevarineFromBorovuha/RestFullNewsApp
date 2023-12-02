package by.home.fullRestApp.exception;

import by.home.fullRestApp.exception.customException.CommonEntityNotFoundException;
import by.home.fullRestApp.exception.customException.SearchEntityByCriteriaException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // В этом методе обрабатываем исключения, выбрасываемые при отсутствии нужной сущности
    @ExceptionHandler({CommonEntityNotFoundException.class, EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex) {

        String message = "Entity not found";
        String debugMessage = ex.getMessage();
        ApiForError apiForError = new ApiForError(message, debugMessage);

        return new ResponseEntity<>(apiForError, HttpStatus.NOT_FOUND);
    }

    // В этом методе обрабатываем и собираем в список исключения, выбрасываемые при неудачной проверки параметров метода
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {

        List<String> errorsList = ex.getConstraintViolations()
                .stream()
                .map(x -> x.getMessage())
                .collect(Collectors.toList());

        ApiForError apiForError = new ApiForError("Method Parameters Not Valid", ex.getMessage(), errorsList);
        return new ResponseEntity<>(apiForError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SearchEntityByCriteriaException.class)
    protected ResponseEntity<Object> handleSearchEntityByCriteriaException(RuntimeException ex) {

        String message = "Error when searching for an entity by criteria ";
        String debugMessage = ex.getMessage();
        ApiForError apiForError = new ApiForError(message, debugMessage);
        return new ResponseEntity<>(apiForError, HttpStatus.NOT_FOUND);
    }

    // В этом методе обрабатываем и собираем в список исключения, выбрасываемые при ошибках валидации
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errorsList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ApiForError apiForError = new ApiForError("Method Argument Not Valid", ex.getMessage(), errorsList);
        return new ResponseEntity<>(apiForError, status);
    }

    // В этом методе обрабатываем исключения, выбрасываемые при отсутствии нужного обработчика
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String message = "No Handler Found";
        String debugMessage = ex.getMessage();
        ApiForError apiForError = new ApiForError(message, debugMessage);

        return new ResponseEntity<>(apiForError, status);

    }

    // В этом методе обрабатываем исключения выбрасываемые при нечитаемом теле запроса
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String message = "Malformed JSON Request";
        ApiForError apiForError = new ApiForError(message, ex.getMessage());
        return new ResponseEntity<>(apiForError, status);

    }

    //  В этом методе реализован обработчик по умолчанию
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ApiForError apiError = new ApiForError("something went wrong", ex.getMessage());
        log.warn(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
