package business.hub.userinfoservice.service;

import business.hub.userinfoservice.dto.UserDto;
import business.hub.userinfoservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.function.Supplier;

/**
 * Конфигурационный класс для поставщика сообщений о днях рождения пользователей.
 * Определяет бин, который предоставляет Supplier для отправки сообщений
 * о днях рождения пользователей.
 */
@Configuration
public class BirthdayMessageSupplier {

    @Autowired
    private UserRepository userRepository; // Инъекция репозитория
    /**
     * Создание Supplier для отправки сообщений о днях рождения пользователей.
     *
     * @return Supplier, который возвращает Flux пользовательских DTO.
     */
    @Bean
    public Supplier<Flux<UserDto>> sendBirthdayMessage() {
        return () -> Flux.fromIterable(userRepository.findByBirthday(LocalDate.now()))
                .map(user -> new UserDto(
                        user.getId(), user.getFirstName(),
                        user.getLastName(), user.getEmail(),
                        user.getBirthday()));
    }
}
