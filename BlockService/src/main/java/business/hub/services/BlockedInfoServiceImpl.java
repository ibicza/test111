package business.hub.services;

import business.hub.dto.BlockAccountInfoDto;
import business.hub.model.BlockedInfo;
import business.hub.repositories.BlockedInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Сервис для работы с информацией о заблокированных пользователях.
 */
@Service
@Transactional
public class BlockedInfoServiceImpl implements BlockedInfoService {

    private final BlockedInfoRepository blockedInfoRepository;

    /**
     * Конструктор с инъекцией репозитория.
     *
     * @param blockedInfoRepositoryParam
     * - репозиторий для хранения информации о заблокированных пользователях
     */
    @Autowired
    public BlockedInfoServiceImpl(final BlockedInfoRepository blockedInfoRepositoryParam) {
        this.blockedInfoRepository = blockedInfoRepositoryParam;
    }

    /**
     * Сохраняет информацию о заблокированном пользователе.
     *
     * @param blockAccountInfoDto - DTO с информацией о заблокированном пользователе
     */
    @Override
    public void saveBlockedInfo(final BlockAccountInfoDto blockAccountInfoDto) {
        BlockedInfo blockedInfo = BlockedInfo.builder()
                .accountId(blockAccountInfoDto.getAccountId())
                .reason(blockAccountInfoDto.getReason())
                .date(LocalDate.now())
                .build();
        blockedInfoRepository.save(blockedInfo);
    }

    /**
     * Проверяет, заблокирован ли пользователь по его идентификатору.
     *
     * @param id - идентификатор пользователя
     * @return true, если пользователь заблокирован, false - в противном случае
     */
    @Override
    public boolean isAccountBlocked(final Long id) {
        Optional<BlockedInfo> blockedInfo = blockedInfoRepository.findById(id);
        return blockedInfo.isPresent();
    }
}
