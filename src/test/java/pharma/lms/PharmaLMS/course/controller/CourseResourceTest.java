package pharma.lms.PharmaLMS.course.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pharma.lms.PharmaLMS.course.service.CourseService;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithMockUser(username = "admin", password = "admin")
class CourseResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseResource courseResource;

    @Mock
    private CourseService courseService;

    @Test
    void shouldGetAllCoursesTest() throws Exception {
        this.mockMvc.perform(get("/course/all"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }
}