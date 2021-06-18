package pharma.lms.PharmaLMS.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.presentation.service.PresentationService;

@Controller
@RequestMapping("/presentations")
public class PresentationResource {

    @Autowired
    private PresentationService presentationService;

    @GetMapping("/all")
    public String get(Model model) {
        List<Presentation> docs = presentationService.getFiles();
        model.addAttribute("docs", docs);
        return "presentation/pres";
    }

    @PostMapping("/uploadFiles")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file: files) {
            presentationService.saveFile(file);

        }
        return "redirect:/presentations/all";
    }
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId){
        Presentation doc = presentationService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getPresType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\"" + doc.getPresName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }

}
