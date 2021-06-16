package pharma.lms.PharmaLMS.course;

import pharma.lms.PharmaLMS.course.presentation.Presentation;
import pharma.lms.PharmaLMS.user.Department;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private Long id;
    private String courseName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_presentations",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "presentation_id") }
    )
    private Set<Presentation> presentations = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Department department;

    public Course() {
    }

    public Course(Long id,
                  String courseName,
                  Department department) {
        this.id = id;
        this.courseName = courseName;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Presentation> getPresentations() {
        return presentations;
    }

    public void setPresentations(Set<Presentation> presentations) {
        this.presentations = presentations;
    }
}
