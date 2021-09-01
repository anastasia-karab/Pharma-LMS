package pharma.lms.PharmaLMS.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.course.service.CourseService;
import pharma.lms.PharmaLMS.message.Message;
import pharma.lms.PharmaLMS.message.MessageConsumer;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pharmalms/mycourses")
public class UserResource {
    private final UserService userService;
    private final CourseService courseService;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public UserResource(UserService userService,
                        CourseService courseService,
                        JmsTemplate jmsTemplate) {
        this.userService = userService;
        this.courseService = courseService;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping()
    public String mine(@ModelAttribute("user") User user,
                       Model model) {
        String currentUsername = userService.getCurrentUserLogin();

        if (currentUsername == "admin") {
            return "admin/admin-page";
        }

        user = userService.findUserByUsername(currentUsername);
        model.addAttribute("user", user);

        List<Course> courses = courseService.findAllCourses();
        for (Course c : courses) {
            if (c.getDepartment() == user.getDepartment()) {
                model.addAttribute("courses", courseService.findCoursesByDepartment(c.getDepartment()));
            }
        }

        return "user/user-profile";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user/user-list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/results/{id}")
    public String userResults(@PathVariable("id") Long userId,
                              Model model) {
        model.addAttribute("id", userId);
        model.addAttribute("results", userService.findUserQuizResults(userId));
        return "user/user-results";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/results/{id}/message")
    public String sendMessageToUserForm(@PathVariable("id") Long userId,
                                        Model model) {
        model.addAttribute("id", userId);
        model.addAttribute("message", new Message());
        return "user/send-message";
    }

    @PostMapping("/users/results/{id}/message")
    public ResponseEntity<String> sendMessageToUser(@PathVariable("id") Long userId,
                                                    @ModelAttribute("message") Message message,
                                                    Model model) {
        message.setRecipientId(userId);

        jmsTemplate.convertAndSend("pharmaqueue", message);
        return new ResponseEntity<String>(
                "Сообщение пользователю " + userService.findUserById(userId).getUsername() + " отправлено",
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/messages")
    public String readMessages(Model model) {
//        jmsTemplate.receiveAndConvert("pharmaqueue").toString();

        String currentUserLogin = userService.getCurrentUserLogin();
        User currentUser = userService.findUserByUsername(currentUserLogin);
        Long id = currentUser.getId();

        List<Message> currentUserMessages = MessageConsumer.getMessages()
                .stream()
                .filter(m -> m.getRecipientId().equals(id))
                .collect(Collectors.toList());

        model.addAttribute("messages", currentUserMessages);
        return "user/read-message";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/results/{resultId}")
    public String deleteUnsatisfactoryResult(@PathVariable("resultId") Long resultId) {
        userService.deleteResult(resultId);
        return "redirect:/pharmalms/mycourses/users";
    }
}
