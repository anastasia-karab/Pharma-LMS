package pharma.lms.PharmaLMS.course.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pharma.lms.PharmaLMS.presentation.domain.Presentation;
import pharma.lms.PharmaLMS.user.domain.Department;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private Long id;
    @Column(name = "course_name", length = 50, nullable = false, unique = true)
    private String courseName;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "course_presentations",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "presentation_id") }
    )
    private Set<Presentation> presentations = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 35, nullable = false)
    private Department department;

    public Course(String courseName,
                  Department department) {
        this.courseName = courseName;
        this.department = department;
    }
}
