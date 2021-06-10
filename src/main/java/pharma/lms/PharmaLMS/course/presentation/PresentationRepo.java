package pharma.lms.PharmaLMS.course.presentation;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.course.presentation.Presentation;

public interface PresentationRepo extends JpaRepository<Presentation, Long> {
}
