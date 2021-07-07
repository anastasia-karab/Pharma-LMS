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

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/quiz/{quizId}")
    public String doQuiz(@PathVariable("quizId") Long quizId,
                         Model model) throws FileNotFoundException {
        Quiz quizName = quizService.getFile(quizId).get();

        Quiz parsedQuiz = quizService.parse(quizName.getQuizName());
        List<Question> questionList = parsedQuiz.getQuestions();
        model.addAttribute("questionList", questionList);

        return "quiz/questions";
    }

    @GetMapping("/test/{quizId}")
    public String showQuestions(@PathVariable("quizId") Long quizId,
                                Model model) throws FileNotFoundException {
        String quizName = quizService.getQuizById(quizId).getQuizName();

        Quiz quiz = quizService.parse(uploadPath + "/" + quizName);
        List<Question> questionList = quiz.getQuestions();
        model.addAttribute("questionList", questionList);

        return "quiz/questions";
    }

    @PostMapping("/submit")
    public String submitQuiz(HttpServletRequest request) {
        int score = 0;
        String[] questionIds = request.getParameterValues("questionId");
        boolean isAnswerCorrect = false;
        for (String questionId : questionIds) {
            try {
                isAnswerCorrect = quizService.isAnswerCorrect(Integer.valueOf(questionId));
                // needs to be resolved
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }

            if (isAnswerCorrect == true) {
                score++;
            }
        }
            request.setAttribute("score", score);
            return "quiz/result";
    }

    @GetMapping("/add")
    public String newPresentation(Model model) {
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

}
