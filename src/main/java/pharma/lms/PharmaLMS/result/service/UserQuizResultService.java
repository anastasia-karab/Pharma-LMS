package pharma.lms.PharmaLMS.result.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;
import pharma.lms.PharmaLMS.result.repo.UserQuizResultRepo;
import pharma.lms.PharmaLMS.user.domain.User;

import java.util.List;

@Service
public class UserQuizResultService {
    private UserQuizResultRepo userQuizResultRepo;

    @Autowired
    public UserQuizResultService(UserQuizResultRepo userQuizResultRepo) {
        this.userQuizResultRepo = userQuizResultRepo;
    }

    public UserQuizResult addResult(UserQuizResult result) {
        return userQuizResultRepo.save(result);
    }

    public List<UserQuizResult> showAllQuizResultsByUser(User user) {
        return userQuizResultRepo.getResultsByUser(user);
    }
}
