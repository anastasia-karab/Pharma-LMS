package pharma.lms.PharmaLMS.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LmsErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.FORBIDDEN.value() || statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                return "error/error-403";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/error-404";
            }
        }
        return "error/default-error-page";
    }
}
