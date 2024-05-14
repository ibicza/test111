package business.hub.services;

import business.hub.dto.DtoCreateEvent;
import business.hub.entity.ProfileEvent;
import business.hub.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfileEventCreatingServices {

    private final Logger logger = LoggerFactory.getLogger(ProfileEventCreatingServices.class);

    private final ProfileRepository profileRepository;

    public ProfileEventCreatingServices(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }



    @Transactional
    public void saveToDatabase(DtoCreateEvent dto) {

        ProfileEvent event = new ProfileEvent();

        event.setAccountId(dto.getAccountId());
        event.setFirstName(dto.getFirstName());
        event.setLastName(dto.getLastName());

        profileRepository.save(event);
    }

}
