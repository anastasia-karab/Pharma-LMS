package pharma.lms.PharmaLMS.course.presentation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PresentationService {
    @Autowired
    private PresentationRepo presentationRepo;

    public Presentation saveFile(MultipartFile file) {
        String docname = file.getOriginalFilename();
        try {
            Presentation doc = new Presentation(docname,file.getContentType(),file.getBytes());
            return presentationRepo.save(doc);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Presentation> getFile(Long fileId) {
        return presentationRepo.findById(fileId);
    }

    public List<Presentation> getFiles(){
        return presentationRepo.findAll();
    }
}
