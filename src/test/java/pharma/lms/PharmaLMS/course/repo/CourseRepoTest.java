package pharma.lms.PharmaLMS.course.repo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.user.domain.Department;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class CourseRepoTest {

    @Autowired
    private CourseRepo courseRepo;

    @Test
    void findCourseByIdTest() {
        Course course = new Course("Стерилизация", Department.Производство);

        courseRepo.save(course);

        Optional<Course> exists = courseRepo.findCourseById(1L);

        assertThat(exists).isNotNull();
    }
}