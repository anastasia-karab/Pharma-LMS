package pharma.lms.PharmaLMS.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.quiz.domain.Question;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.service.QuizService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class QuizResource {
    private QuizService quizService;

    @Autowired
    public QuizResource(QuizService quizService) {
        this.quizService = quizService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/all")
    public String get(Model model) {
        List<Quiz> quizzes = quizService.getQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "quiz/q";
    }

    @PostMapping("/uploadFiles")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file: files) {
            quizService.saveFile(file);
        }
        return "redirect:/quizzes/all";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId){
        Quiz doc = quizService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getQuizType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\"" + doc.getQuizName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @GetMapping("/test")
    public String doQuiz(@RequestParam("quizId") Long quizId,
                                           Model model) throws FileNotFoundException {
        String quizName = quizService.getQuizById(quizId).getQuizName();

        Quiz quiz = quizService.parse(uploadPath + "/" + quizName);
        List<Question> questionList = quiz.getQuestions();

        for (int i = 0; i < questionList.size(); i++) {
            String questionName = "question" + i;
            Question question = questionList.get(i);
            model.addAttribute(questionName, question);
        }

        model.addAttribute("id", quizId);

        return "quiz/servlet/servlet-questions";
    }

    @GetMapping("/add")
    public String newQuiz(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "quiz/upload-to-dir";
    }

    @PostMapping("/add")
    public String putQuizIntoUploadPath(@ModelAttribute("quiz") Quiz quiz,
                                        @RequestParam("quizFile") MultipartFile quizFile) throws IOException {
        if (quizFile != null && !quizFile.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String name = quizFile.getOriginalFilename();

            quizFile.transferTo(new File(uploadPath + "/" + name));

            quiz.setQuizFileDir(uploadPath + "/" + name);
            byte[] quizData = quizFile.getBytes();
            quiz.setData(quizData);

            List<Question> questions = quizService.parse(quiz.getQuizFileDir()).getQuestions();
            quiz.setQuestions(questions);
        }

        quizService.saveFile(quizFile);
        return "redirect:/quizzes/all";
    }

    @GetMapping("/create")
    public String createNewQuiz() {
        return "quiz/servlet/servlet-post-new";
    }
}
