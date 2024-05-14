package business.hub.clientprofileservice.exception;

public class ClientNotFoundException extends RuntimeException {
    /**
     * Исключение, выбрасываемое при отсутствии клиента.
     * Генерируется при попытке доступа к клиенту по его идентификатору, которого не существует.
     * @param id Идентификатор клиента, который не был найден.
     */
    public ClientNotFoundException(final int id) {
        super(String.format("Client with id %d not found", id));
    }
}
