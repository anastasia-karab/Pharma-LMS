package pharma.lms.PharmaLMS.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;
import pharma.lms.PharmaLMS.result.service.UserQuizResultService;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.repo.UserRepo;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserQuizResultService userQuizResultService;

    @Autowired
    public UserService(UserRepo userRepo,
                       UserQuizResultService userQuizResultService) {
        this.userRepo = userRepo;
        this.userQuizResultService = userQuizResultService;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public User findUserById(Long id) {
        return userRepo.findUserById(id);
    }

    public User findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    public String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails securityUser = (UserDetails) authentication.getPrincipal();
                userName = securityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public List<UserQuizResult> findUserQuizResults(Long id) {
        return userQuizResultService.showAllQuizResultsByUser(userRepo.findUserById(id));
    }

    public void deleteResult(Long quizId) {
        UserQuizResult result = userQuizResultService.getUserQuizResultById(quizId);
        userQuizResultService.deleteUserQuizResult(result);
    }
}
