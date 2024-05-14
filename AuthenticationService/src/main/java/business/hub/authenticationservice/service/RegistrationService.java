package business.hub.authenticationservice.service;

import business.hub.authenticationservice.jwt_utils.AccessTokenUtil;
import business.hub.authenticationservice.dto.UserDTO;
import business.hub.authenticationservice.repositry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервис для регистрации новых пользователей.
 */
@Service
@RequiredArgsConstructor
public class RegistrationService {

    /**
     * Репозиторий для работы с пользователями.
     */
    private final UserRepository userRepository;

    /**
     * Сервис для работы с ролями пользователей.
     */
    private final RoleService roleService;

    /**
     * Кодировщик паролей для шифрования паролей пользователей.
     */
    private final BCryptPasswordEncoder passEncoder;

    /**
     * Утилита для работы с JWT токенами доступа.
     */
    private final AccessTokenUtil accessTokenUtil;

    /**
     * Регистрация нового пользователя в системе.
     *
     * @param userDTO объект с данными нового пользователя
     */
    public void registerUser(final UserDTO userDTO) {
        userDTO.setPassword(passEncoder.encode(userDTO.getPassword()));
        if (userDTO.getRoles() == null) {
            userDTO.setRoles(roleService.getDefaultRole());
        }
        userRepository.save(userDTO);
    }

}
