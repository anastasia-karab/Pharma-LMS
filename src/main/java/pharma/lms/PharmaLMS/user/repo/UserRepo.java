package pharma.lms.PharmaLMS.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.user.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserById(Long id);
}
