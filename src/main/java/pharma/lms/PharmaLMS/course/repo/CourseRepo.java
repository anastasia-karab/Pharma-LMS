package pharma.lms.PharmaLMS.course.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.user.domain.Department;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {

    Optional<Course> findCourseById(Long id);

    @Query("SELECT c FROM Course c WHERE c.department = :department")
    List<Course> getCoursesByDepartment(@Param("department") Department department);
}
