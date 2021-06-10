package pharma.lms.PharmaLMS.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharma.lms.PharmaLMS.course.Course;
import pharma.lms.PharmaLMS.course.CourseNotFoundException;
import pharma.lms.PharmaLMS.course.CourseRepo;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepo courseRepo;

    @Autowired
    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
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

    public void deleteCourseById(Long id) {
        courseRepo.deleteById(id);
    }
}
