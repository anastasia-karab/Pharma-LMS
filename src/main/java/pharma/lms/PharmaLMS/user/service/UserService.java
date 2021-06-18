package pharma.lms.PharmaLMS.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.repo.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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
}
