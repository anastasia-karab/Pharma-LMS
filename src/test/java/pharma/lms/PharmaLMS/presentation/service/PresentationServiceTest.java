package pharma.lms.PharmaLMS.presentation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.presentation.repo.PresentationRepo;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.repo.QuizRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.properties")
public class PresentationServiceTest {

    @Mock
    private PresentationRepo presentationRepo;
    @Mock
    private QuizRepo quizRepo;
    @InjectMocks
    private PresentationService presentationService;

    @Test
    void saveFileTest() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "new-pres",
                "sample-pres.pptx",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                Files.readAllBytes(Path.of("src/test/resources/sample-pres/sample-pres.pptx"
                )));

        presentationService.saveFile(file);

        ArgumentCaptor<Presentation> presentationArgumentCaptor = ArgumentCaptor.forClass(Presentation.class);

        verify(presentationRepo).save(presentationArgumentCaptor.capture());

        Presentation capturedPresentation = presentationArgumentCaptor.getValue();

        assertThat(capturedPresentation.getPresType()).isEqualTo(file.getContentType());
        assertThat(capturedPresentation.getPresName()).isEqualTo(file.getOriginalFilename());
    }

    @Test
    void getFileTest() {
        presentationService.getFile(23L);

        verify(presentationRepo).findById(23L);
    }

    @Test
    void addQuizToThePresentationTest() {
        when(presentationRepo.getPresentationById(12L)).thenReturn(new Presentation());
        when(quizRepo.getById(56L)).thenReturn(new Quiz());

        presentationService.addQuizToThePresentation(12L, 56L);

        verify(presentationRepo).getPresentationById(12L);
        verify(quizRepo).getById(56L);

        assertThat(presentationRepo.getPresentationById(12L).getQuiz()).isEqualTo(quizRepo.getById(56L));
    }
}