package business.hub.clientprofileservice.controller;


import business.hub.clientprofileservice.dto.ClientProfileDTO;
import business.hub.clientprofileservice.service.ClientProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов, связанных с профилями клиентов.
 * /api/inner/client/{id} - возвращает объект DTO с информацией о профиле клиента.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inner/client")
public class ClientProfileController {
    private final ClientProfileService clientProfileService;

    /**
     * Метод для получения профиля клиента по его идентификатору.
     * @param id Идентификатор клиента.
     * @return Объект DTO с информацией о профиле клиента.
     */
    @GetMapping("/{id}")
    public ClientProfileDTO getClientProfileById(final @PathVariable int id) {
        return clientProfileService.getClientProfileDTOById(id);
    }
}
