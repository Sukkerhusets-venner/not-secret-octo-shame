package prosjekt.kontroller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * Slettes senere
 */
@Controller
public class testKontroller {

    @ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
    public final class ResourceNotFoundException extends RuntimeException {}

    @RequestMapping("/snake")
    public String snake() {
        return "snake";
    }

    @RequestMapping("/taskTester*")
    public String test() {
        return "taskTester";
    }

    @RequestMapping("/testError*") // kaster en feil for å skjekker om vi fanger den
    public String throwError(HttpServletResponse response) throws IOException {
        if (true) {
            throw new ResourceNotFoundException();
        }
        return "index"; // Merk: ikke error - altså fanges feilen (hvis vi returneres til error)
    }

    @RequestMapping(value = "/logincheat")
    public String login() {
        return "Hovedside";
    }

}
