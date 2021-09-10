package pharma.lms.PharmaLMS.presentation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "presentations")
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "presentation_id")
    private Long id;

    @Column(name = "presentation_name", length = 30, nullable = false, unique = true)
    @Size(min = 5, max = 30)
    private String presName;
    @Column(name = "presentation_type", length = 40, nullable = false)
    private String presType;

    @ManyToMany(mappedBy = "presentations")
    private Set<Course> courses = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;

    @Lob
    private byte[] data;

    public Presentation(String presName, String presType, byte[] data) {
        this.presName = presName;
        this.presType = presType;
        this.data = data;
    }
}
