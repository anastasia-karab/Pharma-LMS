package pharma.lms.PharmaLMS.quiz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import pharma.lms.PharmaLMS.quiz.domain.Quiz;
import pharma.lms.PharmaLMS.quiz.repo.QuizRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.properties")
public class QuizServiceTest {

    @Mock
    private QuizRepo quizRepo;
    @InjectMocks
    private QuizService quizService;

    @Test
    public void saveQuizFileTest() throws IOException {
        MockMultipartFile quizFile =
                new MockMultipartFile("quiz-file",
                        "sample-quiz.json",
                        "application/json",
                        Files.readAllBytes(Path.of("src/test/resources/sample-quiz/sample-quiz.json")));

        quizService.saveFile(quizFile);

        ArgumentCaptor<Quiz> quizArgumentCaptor = ArgumentCaptor.forClass(Quiz.class);

        verify(quizRepo).save(quizArgumentCaptor.capture());

        Quiz capturedQuiz = quizArgumentCaptor.getValue();

        assertThat(capturedQuiz.getQuizName()).isEqualTo(quizFile.getOriginalFilename());
        assertThat(capturedQuiz.getQuizType()).isEqualTo(quizFile.getContentType());
        assertThat(capturedQuiz.getData()).isEqualTo(quizFile.getBytes());
    }

    @Test
    public void getQuizByIdTest() {
        quizService.getQuizById(1L);

        verify(quizRepo).getById(1L);
    }

    @Test
    public void getAllQuizzesTest() {
        quizService.getQuizzes();

        verify(quizRepo).findAll();
    }
}
