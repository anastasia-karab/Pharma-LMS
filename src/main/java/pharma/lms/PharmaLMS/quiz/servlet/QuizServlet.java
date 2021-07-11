package pharma.lms.PharmaLMS.quiz.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import pharma.lms.PharmaLMS.quiz.service.QuizService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/your_score")
public class QuizServlet extends HttpServlet {

    @Autowired
    private QuizService quizService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    public int count;
    public String answer;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        count = 0;

        String quizId = request.getParameter("quizId");

        // null : needs to be resolved
        Integer[] correctAnswers = quizService.getCorrectQuizAnswers(Long.valueOf(quizId));

        Enumeration paramNames = request.getParameterNames();

        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if (!paramName.equals("quizId")) {
                String[] paramValues = request.getParameterValues(paramName);
                for (int i = 0; i < paramValues.length; i++) {
                    answer = paramValues[i];
                    if (answer == paramValues[correctAnswers[i]]) {
                        count++;
                    }
                }
            }
            out.println("<h1>Ваш результат: " + count + " из 3</h1>");
        }
    }
}
