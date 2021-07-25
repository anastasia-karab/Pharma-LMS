package pharma.lms.PharmaLMS.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pharma.lms.PharmaLMS.course.service.CourseService;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.presentation.service.PresentationService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseResource {
    private final CourseService courseService;
    private final PresentationService presentationService;

    @Autowired
    public CourseResource(CourseService courseService,
                          PresentationService presentationService) {
        this.courseService = courseService;
        this.presentationService = presentationService;
    }

    @GetMapping("/all")
    public String getAllCourses(Model model) {
        model.addAttribute("courses", courseService.findAllCourses());
        return "course/show-courses";
    }

    @GetMapping("/find/{id}")
    public String getCourseById(@PathVariable("id") Long id,
                                Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        return "course/one-course";
    }

    @GetMapping("/{id}/presentations")
    public String getPresentationsByCourse(@PathVariable("id") Long id,
                                           Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        model.addAttribute("presentations", courseService.showCoursePresentations(id));
        return "course/show-course-presentations";
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/update")
    public String updateCourse(@ModelAttribute("course") Course course,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course/edit-course";
        }
        courseService.updateCourse(course);
        return "redirect:/course/all";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourseById(id);
        return "redirect:/course/all";
    }

    @GetMapping("/{id}/presentations/add")
    public String addPresentation(@PathVariable("id") Long id,
                                  Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        List<Presentation> presList = presentationService.getFiles();
        model.addAttribute("docs", presList);

        return "/course/add-presentation";
    }

    @PostMapping("/newpres/{cid}/{pid}")
    public String choosePresentation(@PathVariable("cid") Long courseId,
                                     @PathVariable("pid") Long presId) {
        courseService.addPresentationToTheCourse(courseId, presId);
        return "redirect:/course/all";
    }
}
