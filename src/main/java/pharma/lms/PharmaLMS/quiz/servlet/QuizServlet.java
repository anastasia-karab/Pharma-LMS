package pharma.lms.PharmaLMS.quiz.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import pharma.lms.PharmaLMS.quiz.domain.Question;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.service.QuizService;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;
import pharma.lms.PharmaLMS.result.service.UserQuizResultService;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/your_score")
public class QuizServlet extends HttpServlet {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserQuizResultService userQuizResultService;

    @Autowired
    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public int count;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String quizIdAsString = request.getParameter("quizId");
        Long quizId = Long.valueOf(quizIdAsString);

        String quizName = quizService.getQuizById(quizId).getQuizName();
        Quiz quiz = quizService.parse(uploadPath + "/" + quizName);
        List<Question> questions = quiz.getQuestions();

        count = 0;
        String question;
        String correctIndex;

        for (int i = 0; i < questions.size(); i++) {
            question = request.getParameter(String.valueOf(i));
            correctIndex = request.getParameter(i + "correctIndex");

            if (questions.get(i).getAnswers()[Integer.parseInt(correctIndex)].equals(question)) {
                count++;
            }
        }
        out.println("<h1>Ваш результат: " + count + " из 10</h1>");

        UserQuizResult result = getUserQuizResult(quizId, out);
        userQuizResultService.addResult(result);
    }

    private UserQuizResult getUserQuizResult(Long quizId, PrintWriter out) {
        String currentUsername = userService.getCurrentUserLogin();
        User user = userService.findUserByUsername(currentUsername);

        Quiz quiz = quizService.getQuizById(quizId);

        if (userQuizResultService.isUserAllowedToTryAgain(user, quiz)) {
            UserQuizResult result = new UserQuizResult(user, quiz, count);
            return result;
        } else {
            out.println("<h1>Вы уже проходили этот тест</h1>");
            return userQuizResultService.getUserScoreForThisQuiz(user, quiz);
        }
    }
}