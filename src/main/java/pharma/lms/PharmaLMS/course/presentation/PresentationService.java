package pharma.lms.PharmaLMS.course.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharma.lms.PharmaLMS.course.presentation.Presentation;
import pharma.lms.PharmaLMS.course.presentation.PresentationRepo;

import java.util.List;

@Service
public class PresentationService {
    private final PresentationRepo presentationRepo;

    @Autowired
    public PresentationService(PresentationRepo presentationRepo) {
        this.presentationRepo = presentationRepo;
    }

    public Presentation addPresentation(Presentation presentation) {
        return presentationRepo.save(presentation);
    }

    public List<Presentation> findAllPresentations() {
        return presentationRepo.findAll();
    }

    public void deletePresentationById(Long id) {
        presentationRepo.deleteById(id);
    }
}
