package pharma.lms.PharmaLMS.quiz.domain;

import pharma.lms.PharmaLMS.presentation.domain.Presentation;

import javax.persistence.*;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String quizName;
    private String quizType;

    @OneToOne(mappedBy = "quiz")
    private Presentation presentation;

    @Lob
    private byte[] data;

    public Quiz() {
    }

    public Quiz(String quizName, String quizType, byte[] data) {
        this.quizName = quizName;
        this.quizType = quizType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }
}
