package business.hub.orderservice.exception.handler;

import business.hub.orderservice.exception.OrderNotFoundException;
import business.hub.orderservice.exception.dto.ApiErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Глобальный обработчик исключений для контроллера заказов.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class OrderExceptionHandler {

    /**
     * Создатель объектов ответа на ошибки API.
     */
    private final ApiErrorResponseCreator apiErrorResponseCreator;
    /**
     * Создатель отладочных сообщений об ошибках.
     */
    private final ErrorDebugMessageCreator errorDebugMessageCreator;

    /**
     * Обрабатывает исключение OrderNotFoundException.
     *
     * @param exception исключение OrderNotFoundException
     * @return объект ответа на ошибку API
     */
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleOrderNotFoundException(final OrderNotFoundException exception) {
        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(exception, HttpStatus.NOT_FOUND);
        log.error("Handle order not found exception: failed: message: {}, debugMessage: {}.",
                apiErrorResponse.message(), errorDebugMessageCreator.buildErrorDebugMessage(exception));

        return apiErrorResponse;
    }

    /**
     * Обрабатывает исключение MethodArgumentNotValidException.
     *
     * @param exception исключение MethodArgumentNotValidException
     * @return объект ответа на ошибку API
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        // Получение списка всех ошибок валидации
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        FieldError fieldError = fieldErrors.get(fieldErrors.size() - 1);

        String message = fieldError.getField() + " " + fieldError.getDefaultMessage();

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(message, HttpStatus.BAD_REQUEST);
        log.error("Handle data validation exception: failed: message: {}, debugMessage: {}.",
                apiErrorResponse.message(), errorDebugMessageCreator.buildErrorDebugMessage(exception));

        return apiErrorResponse;
    }
}
