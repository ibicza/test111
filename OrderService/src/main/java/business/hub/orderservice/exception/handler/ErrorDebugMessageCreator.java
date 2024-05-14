package business.hub.orderservice.exception.handler;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Сервис для создания отладочных сообщений об ошибках.
 */
@Service
public class ErrorDebugMessageCreator {
    /**
     * Шаблон описания ошибки.
     */
    private static final String DESCRIPTION_TEMPLATE
            = "Operation was failed in method: %s that belongs to the class:"
            + " %s. Problematic code line: %d";

    /**
     * Строит отладочное сообщение об ошибке на основе исключения.
     *
     * @param exception исключение
     * @return отладочное сообщение об ошибке
     */
    public String buildErrorDebugMessage(final Exception exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        return Stream.of(stackTrace)
                .findFirst()
                .map(element -> DESCRIPTION_TEMPLATE.formatted(
                        element.getMethodName(),
                        element.getClassName(),
                        element.getLineNumber()))
                .orElse(Strings.EMPTY);
    }
}
