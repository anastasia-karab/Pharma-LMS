package pharma.lms.PharmaLMS.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharma.lms.PharmaLMS.course.presentation.Presentation;
import pharma.lms.PharmaLMS.course.presentation.PresentationRepo;
import pharma.lms.PharmaLMS.user.Department;

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
                .findAll()
                .stream()
                .filter(course -> course.getDepartment() == department)
                .collect(Collectors.toList());
    }

    public Set<Presentation> addPresentationToTheCourse(Long courseId,
                                             Long presentationId) {
        Course course = findCourseById(courseId);
        Presentation presentation = presentationRepo.getById(presentationId);

        Set<Presentation> coursePresentations = course.getPresentations();
        coursePresentations.add(presentation);

        return coursePresentations;
    }

    public void deleteCourseById(Long id) {
        courseRepo.deleteById(id);
    }
}
