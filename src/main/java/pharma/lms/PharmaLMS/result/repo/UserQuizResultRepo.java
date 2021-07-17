package pharma.lms.PharmaLMS.result.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;
import pharma.lms.PharmaLMS.user.domain.User;

import java.util.List;

public interface UserQuizResultRepo extends JpaRepository<UserQuizResult, Long> {

    @Query("SELECT r FROM UserQuizResult r WHERE r.user = :user")
    List<UserQuizResult> getResultsByUser(@Param("user") User user);
}
