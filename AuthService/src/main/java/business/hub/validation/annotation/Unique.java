package business.hub.validation.annotation;

import business.hub.validation.validator.UniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Пользовательская аннотация для проверки уникальности значения поля.
 * Помечает поле, которое должно быть уникальным среди значений в определенной сущности.
 */
@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    /**
     * Сообщение, которое будет выдано в случае нарушения уникальности поля.
     * По умолчанию: "The value is already taken".
     * @return ?
     */
    String message() default "The value is already taken";
    /**
     * Группы, которые могут быть использованы для группировки ограничений.
     * По умолчанию: пустой массив.
     * @return ?
     */
    Class<?>[] groups() default {};
    /**
     * Дополнительные данные о нарушении, которые могут быть переданы клиенту.
     * По умолчанию: пустой массив.
     * @return ?
     */
    Class<? extends Payload>[] payload() default {};
    /**
     * Имя сущности, в которой нужно проверить уникальность значения поля.
     * @return ?
     */
    String entity();
    /**
     * Имя поля, значение которого нужно сделать уникальным в пределах сущности.
     * @return ?
     */
    String fieldName();
}
