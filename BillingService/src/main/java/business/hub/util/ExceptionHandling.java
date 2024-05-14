package business.hub.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Обработчик исключений.
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandling {

    /**
     * Обработчик исключения MyEntityNotFoundException.
     * @param e Исключение MyEntityNotFoundException
     * @return Ответ с сообщением об ошибке и статусом NOT_FOUND
     */
    @ExceptionHandler
    public ResponseEntity<String> handleNotFoundException(
            final MyEntityNotFoundException e) {
        log.error("BILL NOT FOUND");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
