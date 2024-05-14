package business.hub.ticketingservice.model;

public enum Status {
    CONFIRMED("Подтверждено"),
    UNCONFIRMED("Не подтверждено"),
    CANCELLED("Отменено");

    private final String status;

    /**
     * Конструктор перечисления Status.
     *
     * @param statusParam Строковое представление статуса.
     */
    Status(final String statusParam) {
        this.status = statusParam;
    }

    /**
     * Получение строкового представления статуса.
     *
     * @return Строковое представление статуса.
     */
    public String getStatus() {
        return status;
    }
}
