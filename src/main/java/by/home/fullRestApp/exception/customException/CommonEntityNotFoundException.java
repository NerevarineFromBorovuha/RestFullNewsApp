package by.home.fullRestApp.exception.customException;


//Ошибка, выбрасываемая при отсутствии искомой сущности (News,Comment)
public class CommonEntityNotFoundException extends RuntimeException {

    public CommonEntityNotFoundException() {
        super();
    }

    public CommonEntityNotFoundException(String message) {
        super(message);
    }

    public CommonEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonEntityNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CommonEntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
