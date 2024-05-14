package business.hub.authenticationservice.exception;

/**
 * Исключение, выбрасываемое при недопустимом JWT токене.
 */
public class InvalidTokenException extends RuntimeException {

    /**
     * Конструктор класса InvalidTokenException.
     *
     * @param message сообщение об ошибке
     */
    public InvalidTokenException(final String message) {
        super(message);
    }
}
