package pharma.lms.PharmaLMS.course.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.user.domain.Department;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class CourseRepoTest {

    @Autowired
    private CourseRepo courseRepo;

    @Test
    public void findCourseByIdTest() {
        Course course = new Course("Стерилизация", Department.Производство);

        courseRepo.save(course);

        Optional<Course> exists = courseRepo.findCourseById(1L);

        assertThat(exists).isNotNull();
    }

    @Test
    public void getCoursesByDepartmentTest() {
        Course courseOne = new Course("Стерилизация", Department.Производство);
        courseRepo.save(courseOne);
        Course courseTwo = new Course("Автоклав", Department.Производство);
        courseRepo.save(courseTwo);

        List<Course> coursesByDepartment = courseRepo.getCoursesByDepartment(Department.Производство);

        assertThat(coursesByDepartment.stream().count()).isEqualTo(2);
        assertThat(coursesByDepartment.get(0).getCourseName()).isEqualTo(courseOne.getCourseName());
        assertThat(coursesByDepartment.get(1).getCourseName()).isEqualTo(courseTwo.getCourseName());
    }
}