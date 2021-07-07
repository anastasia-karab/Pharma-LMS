package pharma.lms.PharmaLMS.quiz.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.quiz.domain.Question;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.repo.QuestionRepo;
import pharma.lms.PharmaLMS.quiz.repo.QuizRepo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private QuizRepo quizRepo;
    private QuestionRepo questionRepo;

    @Autowired
    public QuizService(QuizRepo quizRepo, QuestionRepo questionRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
    }

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

    public Quiz parse(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();

        FileReader reader = new FileReader(fileName);
        Quiz root = gson.fromJson(reader, Quiz.class);
        return root;
    }

    public Quiz getQuizById(Long id) {
        return quizRepo.getById(id);
    }

    public boolean isAnswerCorrect(Integer questionId) {
        Question question = questionRepo.getById(questionId);
        String[] answers = question.getAnswers();
        int correctIndex = question.getCorrectIndex();
        for (String a : answers) {
            if (a.equals(answers[correctIndex])) {
                return true;
            }
        }
        return false;
    }
}

