package pharma.lms.PharmaLMS.course.presentation;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String presentationName;
    private String presentationFileDir;
    @Lob
    @Column(name = "dir", columnDefinition = "BLOB")
    private MultipartFile presentationFile;

    public Presentation() {
    }

    public Presentation(Long id,
                        String presentationName,
                        String presentationFileDir,
                        MultipartFile presentationFile) {
        this.id = id;
        this.presentationName = presentationName;
        this.presentationFileDir = presentationFileDir;
        this.presentationFile = presentationFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentationName() {
        return presentationName;
    }

    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }

    public String getPresentationFileDir() {
        return presentationFileDir;
    }

    public void setPresentationFileDir(String presentationFile) {
        this.presentationFileDir = presentationFile;
    }

    public MultipartFile getPresentationFile() {
        return presentationFile;
    }

    public void setPresentationFile(MultipartFile presentationFile) {
        this.presentationFile = presentationFile;
    }

    @Override
    public String toString() {
        return "Presentation{" +
                "id=" + id +
                ", presentationName='" + presentationName + '\'' +
                ", presentationFile=" + presentationFileDir +
                '}';
    }
}
