package by.home.fullRestApp.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiForError {

    private String message;
    private String debugMessage;

    // Поле, которое будет использоваться только в случае существования нескольких ошибок (валидация)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ApiForError(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }
}
