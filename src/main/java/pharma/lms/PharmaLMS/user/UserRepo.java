package pharma.lms.PharmaLMS.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.user.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserById(Long id);
}
