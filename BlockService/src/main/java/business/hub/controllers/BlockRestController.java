package business.hub.controllers;

import business.hub.dto.BlockAccountInfoDto;
import business.hub.services.BlockedInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST контроллер для управления информацией о заблокированных пользователях.
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class BlockRestController {

    private final BlockedInfoService blockedInfoService;

    /**
     * Сохраняет информацию о заблокированном пользователе.
     *
     * @param blockAccountInfoDto - DTO с информацией о заблокированном пользователе
     * @throws ResponseStatusException - если пользователь уже заблокирован
     */
    @RequestMapping(value = "/api/admin/account", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public void saveBlockedInfo(final @RequestBody BlockAccountInfoDto blockAccountInfoDto) {
        if (blockedInfoService.isAccountBlocked(blockAccountInfoDto.getAccountId())) {
            blockedInfoService.saveBlockedInfo(blockAccountInfoDto);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is blocked");
        }
    }
}
