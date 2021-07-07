package pharma.lms.PharmaLMS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PharmaLmsApplication implements WebMvcConfigurer {

	@Value("${upload.path}")
	private String uploadPath;

	public static void main(String[] args) {
		SpringApplication.run(PharmaLmsApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations(uploadPath + "/");
	}
}
