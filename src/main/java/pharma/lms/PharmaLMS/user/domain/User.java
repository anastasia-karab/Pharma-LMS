package pharma.lms.PharmaLMS.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pharma.lms.PharmaLMS.result.domain.UserQuizResult;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @Column(name = "full_name", length = 40, nullable = false)
    @Size(max = 40)
    private String name;
    @Column(length = 15, nullable = false, unique = true)
    @Size(max = 15)
    private String username;
    @Column(length = 75, nullable = false)
    @Size(min = 5, max = 15)
    private String password;
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(length = 35, nullable = false)
    private Department department;

    @Column(length = 15, nullable = false)
    @Size(max = 15)
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", length = 6, nullable = false)
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
