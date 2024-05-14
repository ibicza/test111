package business.hub.services;


import business.hub.dto.BlockAccountInfoDto;

public interface BlockedInfoService {
    /**
     * Сохранение BlockedInfo.
     * @param blockAccountInfoDto
     */
    void saveBlockedInfo(BlockAccountInfoDto blockAccountInfoDto);

    /**
     * Проверка, заблокирован ли акканут.
     * @param id - id.
     * @return - boolean.
     */
    boolean isAccountBlocked(Long id);
}
