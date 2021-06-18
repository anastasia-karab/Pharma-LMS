package pharma.lms.PharmaLMS.course.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pharma.lms.PharmaLMS.course.domain.Course;

import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {

    Optional<Course> findCourseById(Long id);

}
