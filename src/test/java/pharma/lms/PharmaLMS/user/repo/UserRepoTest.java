package pharma.lms.PharmaLMS.user.repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pharma.lms.PharmaLMS.user.domain.Department;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.domain.UserRole;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    User user = new User(
            "София",
            "sofia",
            "$2y$12$M/7V3Def38VONfm19rM7D.RctusI2sn2iPfaoSkjHg8KIMpIN0R5q",
            true,
            Department.ООК,
            "Лаборант",
            UserRole.USER
    );

    @Test
    void findUserByUsernameTest() {
        userRepo.save(user);

        User exists = userRepo.findUserByUsername("sofia");

        String name = exists.getName();

        assertThat(name).isEqualTo(user.getName());
    }

    @Test
    void findUserByIdTest() {
        User savedUser = userRepo.save(user);

        Long savedUserId = savedUser.getId();

        User exists = userRepo.findUserById(savedUserId);

        String username = exists.getUsername();

        assertThat(username).isEqualTo(user.getUsername());
    }

    @Test
    void userNotFoundByIdTest() {
        Long id = 341223L;

        User exists = userRepo.findUserById(id);

        assertThat(exists).isNull();
    }
}