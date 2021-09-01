package pharma.lms.PharmaLMS.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import pharma.lms.PharmaLMS.result.service.UserQuizResultService;
import pharma.lms.PharmaLMS.user.domain.Department;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.domain.UserRole;
import pharma.lms.PharmaLMS.user.repo.UserRepo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.properties")
class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    private UserService userService;
    private UserQuizResultService userQuizResultService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepo, userQuizResultService);
    }

    @Test
    void addUserTest() {
        User user = new User(
                "София",
                "sofia",
                "$2y$12$M/7V3Def38VONfm19rM7D.RctusI2sn2iPfaoSkjHg8KIMpIN0R5q",
                true,
                Department.ООК,
                "Лаборант",
                UserRole.USER
        );

        userService.addUser(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepo).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void findUserByIdTest() {
        userService.findUserById(1L);

        verify(userRepo).findUserById(1L);
    }
}