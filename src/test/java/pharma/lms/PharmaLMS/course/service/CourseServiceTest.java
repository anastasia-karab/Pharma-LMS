package pharma.lms.PharmaLMS.course.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.course.repo.CourseRepo;
import pharma.lms.PharmaLMS.presentation.repo.PresentationRepo;
import pharma.lms.PharmaLMS.user.domain.Department;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.properties")
public class CourseServiceTest {

    @Mock
    private CourseRepo courseRepo;
    @Mock
    private PresentationRepo presentationRepo;
    @InjectMocks
    private CourseService courseService;

    @Test
    public void addCourseTest() {
        Course course = new Course("Стерилизация", Department.Производство);

        courseService.addCourse(course);

        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);

        verify(courseRepo).save(courseArgumentCaptor.capture());

        Course capturedCourse = courseArgumentCaptor.getValue();

        assertThat(capturedCourse).isEqualTo(course);
    }

    @Test
    public void findAllCoursesTest() {
        courseService.findAllCourses();

        verify(courseRepo).findAll();
    }

    @Test
    public void updateCourseTest() {
        Course course = new Course("Стерилизация", Department.Производство);

        courseService.updateCourse(course);

        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);

        verify(courseRepo).save(courseArgumentCaptor.capture());

        Course capturedCourse = courseArgumentCaptor.getValue();

        assertThat(capturedCourse).isEqualTo(course);
    }

    @Test
    public void deleteCourseTest() {
        Course course = new Course("Стерилизация", Department.Производство);

        courseService.deleteCourseById(course.getId());

        verify(courseRepo).deleteById(course.getId());
    }
}