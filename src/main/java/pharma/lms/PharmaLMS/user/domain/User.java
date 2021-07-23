package pharma.lms.PharmaLMS.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private Department department;
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserQuizResult> userQuizResults;

    public User(String name,
                String username,
                String password,
                boolean active,
                Department department,
                String jobTitle,
                UserRole userRole) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.active = active;
        this.department = department;
        this.jobTitle = jobTitle;
        this.userRole = userRole;
    }
}
