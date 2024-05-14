package business.hub.clientprofileservice.service;

import business.hub.clientprofileservice.dto.ClientProfileDTO;
import business.hub.clientprofileservice.exception.ClientNotFoundException;
import business.hub.clientprofileservice.model.ClientProfile;
import business.hub.clientprofileservice.repository.ClientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с профилями клиентов.
 */
@Service
@RequiredArgsConstructor
public class ClientProfileService {
    private final ClientProfileRepository clientProfileRepository;

    /**
     * Метод для получения DTO профиля клиента по его идентификатору.
     * @param id Идентификатор клиента.
     * @return Объект DTO с информацией о профиле клиента.
     * @throws ClientNotFoundException если клиент с указанным идентификатором не найден.
     */
    public ClientProfileDTO getClientProfileDTOById(final int id) {
        Optional<ClientProfile> clientProfile = clientProfileRepository.findById(id);
        return convertToDTO(clientProfile.orElseThrow(() -> new ClientNotFoundException(id)));
    }

    /**
     * Преобразует объект класса ClientProfile в объект класса ClientProfileDTO.
     * @param clientProfile Профиль клиента для преобразования.
     * @return Объект DTO с информацией о профиле клиента.
     */
    private ClientProfileDTO convertToDTO(final ClientProfile clientProfile) {
        ClientProfileDTO clientProfileDTO = new ClientProfileDTO();
        clientProfileDTO.setFirstName(clientProfile.getFirstName());
        clientProfileDTO.setLastName(clientProfile.getLastName());
        clientProfileDTO.setEmail(clientProfile.getEmail());
        return clientProfileDTO;
    }
}
