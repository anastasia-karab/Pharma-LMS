package pharma.lms.PharmaLMS.quiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long questionId;

    @Column(length = 150)
    private String question;
    private String[] answers;
    private Long correctIndex;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Question(String question, String[] answers, Long correctIndex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }
}
