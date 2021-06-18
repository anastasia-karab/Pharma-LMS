package pharma.lms.PharmaLMS.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pharma.lms.PharmaLMS.course.service.CourseService;
import pharma.lms.PharmaLMS.course.domain.Course;

import java.io.IOException;

@Controller
@RequestMapping("/course")
public class CourseResource {
    private final CourseService courseService;

    @Autowired
    public CourseResource(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String getAllCourses(Model model) {
        model.addAttribute("courses", courseService.findAllCourses());
        return "course/show-courses";
    }

    @GetMapping("/find/{id}")
    public String getCourseById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        return "course/one-course";
    }

    @GetMapping("/add")
    public String newCourse(Model model) {
        model.addAttribute("course", new Course());
        return "course/new-course";
    }

    @PostMapping()
    public String addCourse(@ModelAttribute("course") Course course,
                              BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "course/new-course";
        }

        courseService.addCourse(course);
        return "redirect:/course/all";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable("id") Long id,
                       Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        return "course/edit-course";
    }

    @PutMapping()
    public String updateCourse(@ModelAttribute("course") Course course,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course/edit-course";
        }
        courseService.updateCourse(course);
        return "/course/show-courses";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourseById(id);
        return "redirect:/course/all";
    }

    @GetMapping("/{id}/presentations/add")
    public String addPresentation(@PathVariable("id") Long id, Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        return "redirect:/presentations/all";
    }

    @PostMapping("/{id}/newpres")
    public String choosePresentation(@ModelAttribute("course") Course course,
                                     @PathVariable("id") Long presId) {
        Long courseId = course.getId();
        courseService.addPresentationToTheCourse(courseId, presId);
        return "redirect:/course/all";
    }

}
