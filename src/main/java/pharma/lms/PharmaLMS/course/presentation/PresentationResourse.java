package pharma.lms.PharmaLMS.course.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/presentation")
public class PresentationResourse {
    private final PresentationService presentationService;

    @Autowired
    public PresentationResourse(PresentationService presentationService) {
        this.presentationService = presentationService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/add")
    public String newPresentation(Model model) {
        model.addAttribute("presentation", new Presentation());
        return "presentation/new-presentation";
    }

    @PostMapping()
    public String addPresentation(@ModelAttribute("presentation") Presentation presentation,
                                  @RequestParam("presentationFile") MultipartFile presentationFile) throws IOException {
        if (presentationFile != null && !presentationFile.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidPres = UUID.randomUUID().toString();
            String resultPresName = uuidPres + "." + presentationFile.getOriginalFilename();

            presentationFile.transferTo(new File(uploadPath + "/" + resultPresName));

            presentation.setPresentationFileDir(uploadPath + "/" + resultPresName);
            presentation.setPresentationFile(presentationFile);
        }

        presentationService.addPresentation(presentation);
        return "success";
    }
}