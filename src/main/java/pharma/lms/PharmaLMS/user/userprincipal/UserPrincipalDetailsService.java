package pharma.lms.PharmaLMS.user.userprincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pharma.lms.PharmaLMS.user.domain.User;
import pharma.lms.PharmaLMS.user.repo.UserRepo;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UserRepo userRepository;

    @Autowired
    public UserPrincipalDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByUsername(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
