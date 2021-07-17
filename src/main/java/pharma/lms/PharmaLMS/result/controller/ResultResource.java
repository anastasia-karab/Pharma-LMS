package pharma.lms.PharmaLMS.result.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;
import pharma.lms.PharmaLMS.result.service.UserQuizResultService;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/results")
public class ResultResource {
    private UserQuizResultService userQuizResultService;
    private UserService userService;

    @Autowired
    public ResultResource(UserQuizResultService userQuizResultService,
                          UserService userService) {
        this.userQuizResultService = userQuizResultService;
        this.userService = userService;
    }

    @GetMapping()
    public String getMyQuizResults(Model model) {
        String currentUsername = userService.getCurrentUserLogin();
        User user = userService.findUserByUsername(currentUsername);

        List<UserQuizResult> userQuizResults = userQuizResultService.showAllQuizResultsByUser(user);
        model.addAttribute("results", userQuizResults);

        return "result/show-user-quiz-results";
    }
}
