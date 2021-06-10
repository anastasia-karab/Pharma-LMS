package pharma.lms.PharmaLMS.course;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.course.Course;

import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {

    Optional<Course> findCourseById(Long id);

}