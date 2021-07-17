package pharma.lms.PharmaLMS.result.domain;

import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.user.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "results")
public class UserQuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;

    private int score;

    public UserQuizResult() {
    }

    public UserQuizResult(User user, Quiz quiz, int score) {
        this.user = user;
        this.quiz = quiz;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
