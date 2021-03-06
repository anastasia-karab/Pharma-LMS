package pharma.lms.PharmaLMS.presentation.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.presentation.repo.PresentationRepo;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.repo.QuizRepo;

@Service
public class PresentationService {
    private final PresentationRepo presentationRepo;
    private final QuizRepo quizRepo;

    @Autowired
    public PresentationService(PresentationRepo presentationRepo, QuizRepo quizRepo) {
        this.presentationRepo = presentationRepo;
        this.quizRepo = quizRepo;
    }

    public Presentation saveFile(MultipartFile file) throws IOException {
        String docName = file.getOriginalFilename();

        Presentation doc = new Presentation(docName, file.getContentType(), file.getBytes());
        return presentationRepo.save(doc);
    }

    public Optional<Presentation> getFile(Long fileId) {
        return presentationRepo.findById(fileId);
    }

    public List<Presentation> getFiles(){
        return presentationRepo.findAll();
    }

    public void addQuizToThePresentation(Long presId,
                                         Long quizId) {
        Presentation presentation = presentationRepo.getPresentationById(presId);
        Quiz quiz = quizRepo.getById(quizId);

        presentation.setQuiz(quiz);
        presentationRepo.save(presentation);
    }

    public Presentation getPresentationById(Long presId) {
        return presentationRepo.getPresentationById(presId);
    }

}
