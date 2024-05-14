package business.hub.authenticationservice.configs;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурационный класс для настройки Web Security.
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    /**
     * Внедрение UserDetailsService для аутентификации пользователей.
     * (AuthenticationManager)
     */
    private final UserDetailsService userDetailsService;

    /**
     * Определение настроек фильтров безопасности.
     * Всем пользователям доступны : /home,/registration,/api/auth/**,/login
     * @param http объект HttpSecurity для настройки безопасности
     * @return цепочка фильтров безопасности
     * @throws Exception если возникли ошибки при настройке безопасности
     */
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/home",
                                "/registration",
                                "/api/auth/**",
                                "/login").permitAll()
                        .anyRequest().authenticated())
                .logout(LogoutConfigurer::permitAll)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    /**
     * Определение менеджера аутентификации.
     *
     * @param authConfig объект AuthenticationConfiguration для настройки аутентификации
     * @return менеджер аутентификации
     * @throws Exception если возникли ошибки при создании менеджера аутентификации
     */
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Настройка DaoAuthenticationProvider.
     *
     * @return DaoAuthenticationProvider с passwordEncoder и
     * реализацией userDetailsService
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    /**
     * Определение кодировщика паролей.
     *
     * @return BCryptPasswordEncoder для кодирования паролей
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
