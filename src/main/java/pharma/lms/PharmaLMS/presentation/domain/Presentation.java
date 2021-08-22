package pharma.lms.PharmaLMS.presentation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;

import javax.persistence.*;
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

    private String presName;
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
