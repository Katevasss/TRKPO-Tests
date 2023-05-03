package documents.service.service;

import documents.dao.UserDao;
import documents.dto.user.UserDto;
import documents.jpa.entity.user.User;
import documents.jpa.entity.user.UserRolesEnum;
import documents.jpa.exceprions.IdNotFoundException;
import documents.jpa.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDao userDaoJpa;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService();
        userService.userRepository = userRepository;
        userService.userDaoJpa = userDaoJpa;
    }

    @Test
    public void testLoadUserByUsername() {
        User userDto = new User();
        userDto.setId(1L);
        userDto.setLogin("user");
        userDto.setPassword("password");
        userDto.setRole(UserRolesEnum.USER);
        Mockito.when(userRepository.findUserByLogin(eq("user"))).thenReturn(Optional.of(userDto));
        Mockito.when(userRepository.findUserByLogin(eq("nonexistinguser"))).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> userService.loadUserByUsername("user"));
        Assertions.assertThrows(IdNotFoundException.class, () -> userService.loadUserByUsername("nonexistinguser"));
    }

    @Test
    public void testAddNewUser() {
        UserDto userDto = UserDto.builder().build();
        userDto.setId(1L);
        userDto.setLogin("user");
        userDto.setPassword("password");
        userDto.setRole("USER");

        Mockito.when(userDaoJpa.addNewUser(Mockito.any(UserDto.class))).thenReturn(userDto);

        UserDto addedUser = userService.addNewUser(userDto);
        Assertions.assertEquals(userDto, addedUser);
    }

    @Test
    public void testGetCurrentUser() {
        UserDto userDto = UserDto.builder().build();
        userDto.setId(1L);
        userDto.setLogin("user");
        userDto.setPassword("password");
        userDto.setRole("USER");

        Mockito.when(userDaoJpa.getCurrentUser()).thenReturn(userDto);

        UserDto currentUser = userService.getCurrentUser();
        Assertions.assertEquals(userDto, currentUser);
    }

    @Test
    public void testModifyUser() {
        UserDto userDto = UserDto.builder().build();
        userDto.setId(1L);
        userDto.setLogin("user");
        userDto.setPassword("password");
        userDto.setRole("USER");

        Mockito.when(userDaoJpa.modifyUser(Mockito.any(UserDto.class))).thenReturn(userDto);

        UserDto modifiedUser = userService.modifyUser(userDto);
        Assertions.assertEquals(userDto, modifiedUser);
    }

    @Test
    public void testModifyUserThrowsException() {
        UserDto userDto = UserDto.builder().build();
        userDto.setId(1L);
        userDto.setLogin("user");
        userDto.setPassword("password");
        userDto.setRole("USER");

        Mockito.when(userDaoJpa.modifyUser(Mockito.any(UserDto.class))).thenThrow(IdNotFoundException.class);

        Assertions.assertThrows(IdNotFoundException.class, () -> userService.modifyUser(userDto));
    }
}