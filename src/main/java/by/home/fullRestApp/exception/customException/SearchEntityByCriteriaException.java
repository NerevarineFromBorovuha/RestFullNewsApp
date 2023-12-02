package by.home.fullRestApp.exception.customException;

// Ошибка, выбрасываемая при передаче для поиска по каким-либо критериям некорректных данных
public class SearchEntityByCriteriaException extends RuntimeException {

    public SearchEntityByCriteriaException() {
        super();
    }

    public SearchEntityByCriteriaException(String message) {
        super(message);
    }

    public SearchEntityByCriteriaException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchEntityByCriteriaException(Throwable cause) {
        super(cause);
    }

    protected SearchEntityByCriteriaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
