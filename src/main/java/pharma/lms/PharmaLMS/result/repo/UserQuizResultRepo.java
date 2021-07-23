package pharma.lms.PharmaLMS.result.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;
import pharma.lms.PharmaLMS.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserQuizResultRepo extends JpaRepository<UserQuizResult, Long> {

    @Query("SELECT r FROM UserQuizResult r WHERE r.user = :user")
    List<UserQuizResult> getResultsByUser(@Param("user") User user);

    @Query("SELECT r FROM UserQuizResult r WHERE r.user = :user AND r.quiz = :quiz")
    UserQuizResult getUserQuizResult(@Param("user") User user, @Param("quiz") Quiz quiz);

    @Query("SELECT r FROM UserQuizResult r WHERE r.user = :user AND r.quiz = :quiz")
    Optional<UserQuizResult> checkIfUserHasThisQuizResult(@Param("user") User user, @Param("quiz") Quiz quiz);
}
