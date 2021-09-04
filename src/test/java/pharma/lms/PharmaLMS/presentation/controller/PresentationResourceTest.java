package pharma.lms.PharmaLMS.presentation.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PresentationResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getAllPresentationsTest() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.get
                                ("/presentations/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("presentation/pres"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void uploadMultipleFilesTest() throws Exception {
        MockMultipartFile presFile =
                new MockMultipartFile("files",
                        "sample-pres.pptx",
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                        Files.readAllBytes(Path.of("src/test/resources/sample-pres/sample-pres.pptx"
                        )));

        this.mockMvc
                .perform(multipart("/presentations/uploadFiles")
                        .file(presFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/presentations/all"));
    }
}