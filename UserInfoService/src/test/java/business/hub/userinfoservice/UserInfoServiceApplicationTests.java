package business.hub.userinfoservice;

import business.hub.userinfoservice.client.AuthFeignClient;
import business.hub.userinfoservice.dto.UserDto;
import business.hub.userinfoservice.model.User;
import business.hub.userinfoservice.repository.UserRepository;
import business.hub.userinfoservice.service.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UserInfoService.class)
class UserInfoServiceApplicationTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthFeignClient authFeignClient;

    @Autowired
    private UserInfoService userInfoService;

    private User user1;

    private User user2;

    private UserDto userDto1;

    private UserDto userDto2;

    @BeforeEach
    void setUp() {

        user1 = new User();
        user1.setId(1L);
        user1.setEmail("shrek@kek");

        user2 = new User();
        user2.setId(2L);
        user2.setEmail("fiona@kek");

        userDto1 = new UserDto(1L, "Semen@kek", "Semen", "Semen", LocalDate.MIN);
        userDto2 = new UserDto(2L, "Lena@kek", "Lena", "Tihonova", LocalDate.MIN);

    }

    @Test
    void getUserInfoSuccessTest() {

        List<Long> accountIds = Arrays.asList(userDto1.getId(), userDto2.getId());
        List<User> users = Arrays.asList(user1, user2);
        List<String> emails = Arrays.asList(userDto1.getEmail(), userDto2.getEmail());
        List<UserDto> usersDto = Arrays.asList(userDto1, userDto2);

        when(userRepository.findUsersByIdIn(anyList())).thenReturn(users);
        when(authFeignClient.getUserDetailsByEmails(anyList())).thenReturn(usersDto);

        List<UserDto> result = userInfoService.getUserInfo(accountIds);

        assertEquals(usersDto, result);

        verify(userRepository, times(1)).findUsersByIdIn(accountIds);

    }

}
