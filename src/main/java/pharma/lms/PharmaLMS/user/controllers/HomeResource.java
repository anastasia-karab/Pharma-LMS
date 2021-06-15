package pharma.lms.PharmaLMS.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pharma.lms.PharmaLMS.user.User;
import pharma.lms.PharmaLMS.user.UserRole;
import pharma.lms.PharmaLMS.user.UserService;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping(path = "/")
public class HomeResource {
    private UserService userService;

    @Autowired
    public HomeResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("registration")
    public String createAccount(User user, Map<String, Object> model) {
        User userByUsername = userService.findUserByUsername(user.getUsername());
        if (userByUsername != null) {
            model.put("message", "User exists");
            return "registration";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setUserRoles(Collections.singleton(UserRole.USER));
        userService.addUser(user);

        return "redirect:/login";
    }

}
