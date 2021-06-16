package pharma.lms.PharmaLMS.course.presentation;

import pharma.lms.PharmaLMS.course.Course;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="pres")
public class Presentation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "presentation_id")
    private Long id;

    private String presName;
    private String presType;

    @ManyToMany(mappedBy = "presentations")
    private Set<Course> courses = new HashSet<>();

    @Lob
    private byte[] data;

    public Presentation() {}

    public Presentation(String presName, String presType, byte[] data) {
        this.presName = presName;
        this.presType = presType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresName() {
        return presName;
    }

    public void setPresName(String presName) {
        this.presName = presName;
    }

    public String getPresType() {
        return presType;
    }

    public void setPresType(String presType) {
        this.presType = presType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
