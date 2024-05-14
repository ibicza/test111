package business.hub.authenticationservice;

import business.hub.authenticationservice.dto.AuthenticationRequest;
import business.hub.authenticationservice.dto.AuthenticationResponse;
import business.hub.authenticationservice.dto.Role;
import business.hub.authenticationservice.dto.UserDTO;
import business.hub.authenticationservice.exception.InvalidTokenException;
import business.hub.authenticationservice.jwt_utils.AccessTokenUtil;
import business.hub.authenticationservice.jwt_utils.RefreshTokenUtil;
import business.hub.authenticationservice.repositry.UserRepository;
import business.hub.authenticationservice.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит-тесты для {@link AuthenticationService}.
 * Этот класс использует Mockito для создания заглушек зависимостей
 * и тестирования поведения службы аутентификации.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AuthenticationService.class)
class AuthenticationServiceApplicationTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AccessTokenUtil accessTokenUtil;

    @MockBean
    private RefreshTokenUtil refreshTokenUtil;

    @Autowired
    private AuthenticationService authenticationService;

    private AuthenticationRequest request;

    private UserDTO userDTO;

    /**
     * Метод настройки для инициализации заглушек объектов и тестовых данных
     * перед каждым тестовым методом.
     */
    @BeforeEach
    void setUp() {

        request = new AuthenticationRequest();

        Role role = new Role();
        role.setRole("USER");

        request.setUsername("requestName");
        request.setPassword("password");

        userDTO = new UserDTO();

        userDTO.setUsername("requestName");
        userDTO.setPassword("password");
        userDTO.setRoles(new HashSet<>(List.of(role)));
        userDTO.setId(1);
        userDTO.setEmail("shrek@kek");
        userDTO.setAccessToken("accToken");
        userDTO.setRefreshToken("refreshToken");

    }


    /**
     * Тест успешной аутентификации.
     * Проверяет, что служба аутентификации возвращает действительный ответ при успешной аутентификации.
     */

    @Test
    public void testAuthenticateSuccessTest() {

        Authentication authentication = mock(Authentication.class);

        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(userDTO));
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response, "Response should not be null");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByUsername(userDTO.getUsername());


    }

    /**
     * Тест на ошибку аутентификации.
     * Проверяет, что служба аутентификации возвращает null при неудачной аутентификации.
     */
    @Test
    void authenticateFailureTest() {

        when(authenticationManager.authenticate(any())).thenReturn(null);

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNull(response);
    }

    /**
     * Тест на обновление действительного токена.
     * Проверяет, что служба аутентификации может обновить действительный токен и
     * вернуть действительный ответ.
     */
    // не работает
    @Test
    void testRefreshValidTokenTest() {

        String refreshToken = "refreshToken";
        when(userRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.of(userDTO));
        when(refreshTokenUtil.isTokenValid(refreshToken, userDTO)).thenReturn(true);

        AuthenticationResponse response = authenticationService.refresh(refreshToken);

        assertNotNull(response, "Response should not be null");
        verify(userRepository, times(1)).findByRefreshToken(refreshToken);
        verify(refreshTokenUtil, times(1)).isTokenValid(refreshToken, userDTO);
    }

    /**
     * Тест на обновление недействительного токена.
     * Проверяет, что служба аутентификации выбрасывает исключение InvalidTokenException
     * при попытке обновления недействительного токена.
     */
    @Test
    void refreshFailureTest() {
        String refreshToken = "refreshToken";
        assertThrows(InvalidTokenException.class, () -> authenticationService.refresh(refreshToken));
    }

}

