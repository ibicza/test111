package business.hub.authenticationservice.configs;

import business.hub.authenticationservice.dto.UserDTO;
import business.hub.authenticationservice.repositry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;

/**
 * Конфигурационный класс для настроек приложения.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /**
     * Репозиторий пользователей для доступа к данным пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Бин для UserDetailsService.
     * Извлекает поля Username, Password, Role пользователя из базы данных.
     *
     * @return реализацию UserDetailsService c Username, Password, Role
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            /**
             * Возвращает пользователя по Username.
             *
             * @param username имя пользователя
             * @return UserDetails содержащий userdetails пользователя
             * @throws UsernameNotFoundException если пользователь не найден
             */
            @Override
            public UserDetails loadUserByUsername(final String username)
                    throws UsernameNotFoundException {
                Optional<UserDTO> userDTO = userRepository.findByUsername(username);
                if (userDTO.isEmpty()) {
                    throw new UsernameNotFoundException(String.format("User with username - %s, not found", userDTO));
                }
                return new org.springframework.security.core.userdetails.User(
                        userDTO.get().getUsername(),
                        userDTO.get().getPassword(),
                        userDTO.get().getRoles());
            }
        };
    }

}
