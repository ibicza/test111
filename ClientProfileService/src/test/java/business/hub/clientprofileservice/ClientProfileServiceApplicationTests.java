package business.hub.clientprofileservice;

import business.hub.clientprofileservice.dto.ClientProfileDTO;
import business.hub.clientprofileservice.exception.ClientNotFoundException;
import business.hub.clientprofileservice.model.ClientProfile;
import business.hub.clientprofileservice.repository.ClientProfileRepository;
import business.hub.clientprofileservice.service.ClientProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientProfileServiceApplicationTests {

    @Mock
    private ClientProfileRepository clientProfileRepository;

    @InjectMocks
    private ClientProfileService clientProfileService;

    private ClientProfile clientProfile;

    @BeforeEach
    void setUp() {
        clientProfile = new ClientProfile();
        clientProfile.setId(1);
        clientProfile.setEmail("Kek@Kekov");
        clientProfile.setFirstName("Shrek");
        clientProfile.setLastName("Shrekov");
    }

    @Test
    void getClientProfileDTOByIdTest() {
        when(clientProfileRepository.findById(clientProfile.getId())).thenReturn(Optional.of(clientProfile));

        ClientProfileDTO actualDTO = clientProfileService.getClientProfileDTOById(clientProfile.getId());

        assertEquals("Kek@Kekov", actualDTO.getEmail());
        assertEquals("Shrek", actualDTO.getFirstName());
        assertEquals("Shrekov", actualDTO.getLastName());

        verify(clientProfileRepository, times(1)).findById(clientProfile.getId());
    }

    @Test
    void getClientProfileDTOByIdNotFoundTest() {
        int nonExistentId = 2;
        when(clientProfileRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            clientProfileService.getClientProfileDTOById(nonExistentId);
        });

        verify(clientProfileRepository, times(1)).findById(nonExistentId);
    }
}
