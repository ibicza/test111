package business.hub.authenticationservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс для обработки исключений в контроллерах.
 */
@ControllerAdvice
public class ExceptionHandling {

    /**
     * Внедряем Logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandling.class);

    /**
     * Обработка исключения при неверных учетных данных аутентификации.
     *
     * @param e исключение BadCredentialsException
     * @return ответ с сообщением об ошибке и статусом UNAUTHORIZED
     */
    @ExceptionHandler
    public ResponseEntity<String> handleBadCredentials(final BadCredentialsException e) {
        logger.debug("User with bad credentials exception.");
        return new ResponseEntity<>("Bad credentials", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Обработка исключения при недопустимом JWT токене.
     *
     * @param e исключение InvalidTokenException
     * @return ответ с сообщением об ошибке и статусом UNAUTHORIZED
     */
    @ExceptionHandler
    public ResponseEntity<String> handleJwtTokenExceptions(final InvalidTokenException e) {
        logger.debug("Invalid token exception caught. Exception: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
