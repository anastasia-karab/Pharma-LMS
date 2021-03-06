package pharma.lms.PharmaLMS.quiz.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quiz_name", length = 30, unique = true, nullable = false)
    @Size(min = 5, max = 30)
    private String quizName;
    @Column(name = "quiz_type", length = 40, nullable = false)
    private String quizType;

    @OneToOne(mappedBy = "quiz")
    private Presentation presentation;

    @Lob
    private byte[] data;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
    @JsonProperty("questions")
    private List<Question> questions;

    @Column(name = "file_dir", length = 80)
    private String quizFileDir;

    @Lob
    @Column(name = "dir", columnDefinition = "BLOB")
    @Nullable
    private MultipartFile quizFile;

    @OneToOne(mappedBy = "quiz")
    private UserQuizResult result;

    public Quiz(String quizName, String quizType, byte[] data) {
        this.quizName = quizName;
        this.quizType = quizType;
        this.data = data;
    }
}
