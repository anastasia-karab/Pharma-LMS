package pharma.lms.PharmaLMS.quiz.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.repo.QuizRepo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepo quizRepo;

    @Autowired
    public QuizService(QuizRepo quizRepo) {
        this.quizRepo = quizRepo;
    }

    public Quiz saveFile(MultipartFile file) throws IOException {
        String docName = file.getOriginalFilename();

        Quiz doc = new Quiz(docName, file.getContentType(), file.getBytes());
        return quizRepo.save(doc);
    }

    public Optional<Quiz> getFile(Long fileId) {
        return quizRepo.findById(fileId);
    }

    public List<Quiz> getQuizzes() {
        return quizRepo.findAll();
    }

    public Quiz parse(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();

        FileReader reader = new FileReader(fileName);
        Quiz root = gson.fromJson(reader, Quiz.class);
        return root;
    }

    public Quiz getQuizById(Long id) {
        return quizRepo.getById(id);
    }
}

