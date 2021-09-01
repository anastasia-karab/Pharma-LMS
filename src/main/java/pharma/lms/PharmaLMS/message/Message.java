package pharma.lms.PharmaLMS.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class Message implements Serializable {
    private static final Long serialVersionUID = 1L;

    private String content;
    private Long recipientId;
}
