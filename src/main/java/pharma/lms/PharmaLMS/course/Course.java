package pharma.lms.PharmaLMS.course;

import pharma.lms.PharmaLMS.user.Department;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String courseName;

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

}
