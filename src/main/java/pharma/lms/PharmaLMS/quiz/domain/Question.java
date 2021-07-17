package pharma.lms.PharmaLMS.quiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long questionId;

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

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public Long getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(Long correctIndex) {
        this.correctIndex = correctIndex;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
