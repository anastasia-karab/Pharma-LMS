package pharma.lms.PharmaLMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class PharmaLmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmaLmsApplication.class, args);
	}

}
