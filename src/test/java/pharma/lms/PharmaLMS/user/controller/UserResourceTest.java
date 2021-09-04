package pharma.lms.PharmaLMS.user.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void allUsersTest() throws Exception {
        this.mockMvc
                .perform(get("/pharmalms/mycourses/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/user-list"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deleteUnsatisfactoryResultTest() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete
                                ("/pharmalms/mycourses/users/results/{resultId}", 1L))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pharmalms/mycourses/users"));
    }
}