package business.hub.userinfoservice.controller;

import business.hub.userinfoservice.dto.UserDto;
import business.hub.userinfoservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для управления информацией о пользователях.
 * Отвечает за обработку HTTP-запросов, связанных с получением информации о пользователях.
 */
@RestController
@RequestMapping("/api/inner/profiles")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * Получение информации о пользователях по списку идентификаторов аккаунтов.
     *
     * @param accountIds Список идентификаторов аккаунтов.
     * @return Список информации о пользователях.
     */
    @PostMapping
    public List<UserDto> getUserInfo(final @RequestBody List<Long> accountIds) {
        return userInfoService.getUserInfo(accountIds);
    }

}

