package pharma.lms.PharmaLMS.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.course.exception.CourseNotFoundException;
import pharma.lms.PharmaLMS.course.repo.CourseRepo;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.presentation.repo.PresentationRepo;
import pharma.lms.PharmaLMS.user.domain.Department;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepo courseRepo;
    private final PresentationRepo presentationRepo;

    @Autowired
    public CourseService(CourseRepo courseRepo,
                         PresentationRepo presentationRepo) {
        this.courseRepo = courseRepo;
        this.presentationRepo = presentationRepo;
    }

    public Course addCourse(Course course) {
        return courseRepo.save(course);
    }

    public List<Course> findAllCourses() {
        return courseRepo.findAll();
    }

    public Course updateCourse(Course course) {
        return courseRepo.save(course);
    }

    public Course findCourseById(Long id) {
        return courseRepo
                .findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course by id " + id + " not found"));
    }

    public List<Course> findCoursesByDepartment(Department department) {
        return courseRepo
                .getCoursesByDepartment(department);
    }

    public void addPresentationToTheCourse(Long courseId,
                                           Long presentationId) {
        Course course = findCourseById(courseId);
        Presentation presentation = presentationRepo.getById(presentationId);
        Set<Presentation> presentations = course.getPresentations();

        presentations.add(presentation);
        course.setPresentations(presentations);
        courseRepo.save(course);
    }

    public List<Presentation> showCoursePresentations(Long courseId) {
        Course course = findCourseById(courseId);

        return presentationRepo
                .findAll()
                .stream()
                .filter(presentation -> presentation.getCourses().contains(course))
                .collect(Collectors.toList());
    }

    public void deleteCourseById(Long id) {
        courseRepo.deleteById(id);
    }
}
