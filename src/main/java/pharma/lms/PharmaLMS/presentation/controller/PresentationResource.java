package pharma.lms.PharmaLMS.presentation.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.presentation.service.PresentationService;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.service.QuizService;

@Controller
@RequestMapping("/presentations")
public class PresentationResource {

    private final PresentationService presentationService;
    private final QuizService quizService;

    @Autowired
    public PresentationResource(PresentationService presentationService, QuizService quizService) {
        this.presentationService = presentationService;
        this.quizService = quizService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public String getAllPresentations(Model model) {
        List<Presentation> docs = presentationService.getFiles();
        model.addAttribute("docs", docs);
        return "presentation/pres";
    }

    @PostMapping("/uploadFiles")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        for (MultipartFile file: files) {
            presentationService.saveFile(file);

        }
        return "redirect:/presentations/all";
    }
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId) {
        Presentation doc = presentationService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getPresType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\"" + doc.getPresName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/quizzes/add")
    public String addQuiz(@PathVariable("id") Long id,
                          Model model) {
        model.addAttribute("presentation", presentationService.getPresentationById(id));
        List<Quiz> quizList = quizService.getQuizzes();
        model.addAttribute("quizzes", quizList);

        return "presentation/add-quiz";
    }

    @PostMapping("/newquiz/{pid}/{qid}")
    public String chooseQuiz(@PathVariable("pid") Long presId,
                             @PathVariable("qid") Long quizId) {
        presentationService.addQuizToThePresentation(presId, quizId);
        return "redirect:/presentations/all";
    }
}
