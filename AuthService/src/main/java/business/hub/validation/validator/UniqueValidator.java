package business.hub.validation.validator;


import business.hub.validation.annotation.Unique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Валидатор, который проверяет уникальность значения поля в базе данных.
 */
public class UniqueValidator implements ConstraintValidator<Unique, String> {

    @PersistenceContext
    private EntityManager entityManager;

    private String entityName;
    private String fieldName;

    /**
     * Инициализация валидатора.
     *
     * @param constraintAnnotation аннотация с параметрами проверки уникальности
     */
    @Override
    public void initialize(final Unique constraintAnnotation) {
        this.entityName = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.fieldName();
    }

    /**
     * Проверка, является ли значение поля уникальным в пределах сущности.
     *
     * @param fieldValue значение поля для проверки
     * @param context     контекст проверки ограничений
     * @return true, если значение поля уникально; в противном случае - false
     */
    @Override
    public boolean isValid(final String fieldValue,
                           final ConstraintValidatorContext context) {
        String query = String.format("SELECT count(t) FROM %s t WHERE t.%s = :fieldValue",
                entityName, fieldName);
        long count = entityManager.createQuery(query, Long.class)
                .setParameter("fieldValue", fieldValue)
                .getSingleResult();
        return count == 0;
    }
}
