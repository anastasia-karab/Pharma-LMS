package pharma.lms.PharmaLMS.quiz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;

public interface QuizRepo extends JpaRepository<Quiz, Long> {
}
