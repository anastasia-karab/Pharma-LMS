package pharma.lms.PharmaLMS.quiz.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import pharma.lms.PharmaLMS.quiz.service.QuizService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

@WebServlet("/post_new_quiz")
public class NewQuizServlet extends HttpServlet {

    @Autowired
    private QuizService quizService;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        File quizFile = new File(uploadPath + "/" + request.getParameter("quiz_name") + ".json");

        FileWriter fileWriter = new FileWriter(quizFile);
        fileWriter.write("{\n" +
                "\"questions\": [");
        for (int i = 0; i < 10; i++) {
            fileWriter.append("\n{" +
                    "\n\"question\": \"" + request.getParameter("question" + i) + "\"," +
                    "\n\"answers\": [" +
                    "\n\"" + request.getParameter(i + "answer" + 0) + "\"," +
                    "\n\"" + request.getParameter(i + "answer" + 1) + "\"," +
                    "\n\"" + request.getParameter(i + "answer" + 2) + "\"" +
                    "\n]," +
                    "\n\"correctIndex\": " + request.getParameter("correctIndex" + i) +
                    "\n}");
            if (i != 9) {
                fileWriter.append(",");
            }
        }
        fileWriter.append("\n]" +
                "\n}");

        fileWriter.flush();
        fileWriter.close();

        if (quizFile.exists()) {
            out.println("Файл с тестом успешно создан");
        }

        quizService.saveFile(new MockMultipartFile(quizFile.toString(),
                quizFile.getName(),
                "application/json",
                Files.readAllBytes(quizFile.toPath())));
    }

}
