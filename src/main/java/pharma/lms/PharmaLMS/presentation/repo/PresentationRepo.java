package pharma.lms.PharmaLMS.presentation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;

import java.util.Optional;

public interface PresentationRepo extends JpaRepository<Presentation, Long> {

    Presentation getPresentationById(Long id);

}
