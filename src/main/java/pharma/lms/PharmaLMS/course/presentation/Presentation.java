package pharma.lms.PharmaLMS.course.presentation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="pres")
public class Presentation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String presName;
    private String presType;

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

}
