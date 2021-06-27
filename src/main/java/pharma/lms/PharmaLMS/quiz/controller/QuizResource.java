package pharma.lms.PharmaLMS.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.service.QuizService;

import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class QuizResource {

    @Autowired
    private QuizService quizService;

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
}
