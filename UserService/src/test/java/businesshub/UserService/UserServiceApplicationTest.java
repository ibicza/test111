package businesshub.UserService;

import business.hub.userservice.model.User;
import business.hub.userservice.repositories.RoleRepositories;
import business.hub.userservice.repositories.UserRepositories;
import business.hub.userservice.service.UserServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UserServicesImpl.class)
public class UserServiceApplicationTest {

    @MockBean
    private UserRepositories userRepositories;

    @MockBean
    private RoleRepositories roleRepositories;

    @Autowired
    private UserServicesImpl userServices;

    @MockBean
    private StreamBridge streamBridge;
    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        user.setEmail("kek@Kekov");
        user.setProfileStatus("200");
        user.setPassword("777");
        user.setRoles(new ArrayList<>());
        user.setFirstName("Utka");
        user.setLastName("Guse");
        user.setUsername("Utkativ");
    }


    @Test
    void getAllUsersTest() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("userTest1");

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("userTest2");
        List<User> userList = new ArrayList<>(List.of(user1, user2));
        when(userRepositories.findAll()).thenReturn(userList);

        List<User> resList = userServices.getAllUsers();

        assertEquals(userList, resList);
        assertThat(resList, containsInAnyOrder(user1, user2));
        verify(userRepositories).findAll();
    }

    @Test
    void getUserTest() {

        when(userRepositories.findById(user.getId())).thenReturn(Optional.of(user));

        User newUser = userServices.getUser(user.getId());

        assertEquals(user, newUser);

    }

    @Test
    void saveUserTest() {
        userServices.saveUser(user);
        verify(userRepositories, times(1)).save(user);

    }

    @Test
    void deleteUserTest() {
        when(userRepositories.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepositories.existsById(user.getId())).thenReturn(true);

        userServices.deleteUser(user.getId());

        verify(userRepositories, times(1)).deleteById(user.getId());
    }

    @Test
    void updateProfileStatusTest() {

        when(userRepositories.findById(1)).thenReturn(Optional.of(user));

        userServices.updateProfileStatus(1, "CREATED");

        verify(userRepositories).findById(1);
        verify(userRepositories).save(any(User.class));
    }

    @Test
    void handleProfileCreationEventTest() {

        when(userRepositories.findById(1)).thenReturn(Optional.of(user));

        userServices.handleProfileCreationEvent(1, true);

        verify(userRepositories).findById(1);
    }
}
