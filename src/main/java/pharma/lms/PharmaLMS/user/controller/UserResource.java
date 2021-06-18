package pharma.lms.PharmaLMS.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.course.service.CourseService;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/pharmalms/mycourses/")
public class UserResource {
    private UserService userService;
    private CourseService courseService;

    @Autowired
    public UserResource(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String myProfile(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);

        List<Course> courses = courseService.findAllCourses();
        for (Course c : courses) {
            if (c.getDepartment() == user.getDepartment()) {
                model.addAttribute("courses", courseService.findCoursesByDepartment(c.getDepartment()));
            }
        }

        return "user/user-profile";
    }

}
