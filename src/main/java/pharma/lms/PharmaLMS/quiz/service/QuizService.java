package pharma.lms.PharmaLMS.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.repo.QuizRepo;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepo quizRepo;

    public Quiz saveFile(MultipartFile file) {
        String docName = file.getOriginalFilename();
        try {
            Quiz doc = new Quiz(docName, file.getContentType(), file.getBytes());
            return quizRepo.save(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Quiz> getFile(Long fileId) {
        return quizRepo.findById(fileId);
    }

    public List<Quiz> getQuizzes() {
        return quizRepo.findAll();
    }
}
