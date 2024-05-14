package business.hub.orderservice.exception.handler;

import business.hub.orderservice.exception.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApiErrorResponseCreator {
    /**
     * Строит объект ответа на ошибку API.
     *
     * @param errorMessage сообщение об ошибке
     * @param httpStatus   HTTP статус ошибки
     * @return объект ответа на ошибку API
     */
    public ApiErrorResponse buildResponse(final String errorMessage,
                                          final HttpStatus httpStatus) {
        return ApiErrorResponse.builder()
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .httpStatusCode(httpStatus.value())
                .build();
    }
    /**
     * Строит объект ответа на ошибку API на основе исключения.
     *
     * @param exception  исключение
     * @param httpStatus HTTP статус ошибки
     * @return объект ответа на ошибку API
     */
    public ApiErrorResponse buildResponse(final Exception exception,
                                          final HttpStatus httpStatus) {
        return buildResponse(exception.getMessage(), httpStatus);
    }
}
