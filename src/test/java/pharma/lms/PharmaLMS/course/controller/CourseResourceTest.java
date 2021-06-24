package pharma.lms.PharmaLMS.course.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pharma.lms.PharmaLMS.course.domain.Course;
import pharma.lms.PharmaLMS.course.service.CourseService;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.presentation.service.PresentationService;
import pharma.lms.PharmaLMS.user.domain.Department;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
public class CourseResourceTest {
    @Mock
    private CourseService courseService;
    @Mock
    private PresentationService presentationService;
    @InjectMocks
    private CourseResource courseResource;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(courseResource).build();
    }

    @Test
    public void showAllCoursesTest() throws Exception {
        Mockito
                .when(courseService.findAllCourses())
                .thenReturn(Collections.singletonList(new Course("Стерилизация", Department.Производство)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/course/all")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("course/show-courses"));
    }

    @Test
    public void showCourseByIdTest() throws Exception {
        Mockito
                .when(courseService.findCourseById(any()))
                .thenReturn(new Course("Стерилизация", Department.Производство));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/course/find/{id}", 1L)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("course/one-course"));
    }

    @Test
    public void addNewCourseTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/course")
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course/all"));
    }

    @Test
    public void showCoursePresentationsTest() throws Exception {
        Mockito
                .when(courseService.showCoursePresentations(any()))
                .thenReturn(Collections.singletonList(new Presentation()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/course/{id}/presentations", 1L)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("course/show-course-presentations"));
    }
}

