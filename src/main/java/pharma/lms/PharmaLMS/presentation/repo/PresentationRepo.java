package pharma.lms.PharmaLMS.presentation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;

public interface PresentationRepo extends JpaRepository<Presentation, Long> {
}
