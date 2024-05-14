package business.hub.util;

/**
 * Исключение, выбрасываемое при отсутствии сущности.
 */
public class MyEntityNotFoundException extends RuntimeException {

    /**
     * Конструктор для исключения MyEntityNotFoundException.
     *
     * @param id Идентификатор сущности, которая не была найдена
     */
    public MyEntityNotFoundException(final long id) {
        super(String.format("Entity with ID = %d not found", id));
    }

}
