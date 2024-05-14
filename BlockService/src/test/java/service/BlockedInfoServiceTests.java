package service;

import business.hub.dto.BlockAccountInfoDto;
import business.hub.model.BlockedInfo;
import business.hub.repositories.BlockedInfoRepository;
import business.hub.services.BlockedInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BlockedInfoServiceTests {

    @Mock
    private BlockedInfoRepository blockedInfoRepository;

    @InjectMocks
    private BlockedInfoServiceImpl blockedInfoService;

    private BlockAccountInfoDto blockAccountInfoDto;
    private BlockedInfo blockedInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        blockAccountInfoDto = new BlockAccountInfoDto();
        blockAccountInfoDto.setAccountId(1L);
        blockAccountInfoDto.setReason("Test reason");

        blockedInfo = new BlockedInfo();
        blockedInfo.setAccountId(1L);
        blockedInfo.setReason("Test reason");
        blockedInfo.setDate(LocalDate.now());
    }

    @Test
    void saveBlockedInfo() {

        blockedInfoService.saveBlockedInfo(blockAccountInfoDto);
        verify(blockedInfoRepository, times(1)).save(any(BlockedInfo.class));
    }

    @Test
    void isAccountBlocked() {
        Long id = 1L;

        when(blockedInfoRepository.findById(id)).thenReturn(Optional.of(blockedInfo));

        boolean result = blockedInfoService.isAccountBlocked(id);

        assertTrue(result);
        verify(blockedInfoRepository, times(1)).findById(id);
    }

    @Test
    void isAccountNotBlocked() {
        Long id = 1L;

        when(blockedInfoRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = blockedInfoService.isAccountBlocked(id);

        assertFalse(result);
        verify(blockedInfoRepository, times(1)).findById(id);
    }
}
