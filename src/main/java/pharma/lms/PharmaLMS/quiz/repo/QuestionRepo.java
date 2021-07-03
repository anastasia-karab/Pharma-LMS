package pharma.lms.PharmaLMS.quiz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.quiz.domain.Question;

public interface QuestionRepo extends JpaRepository<Question, Long> {
}
