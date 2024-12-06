package bookku;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Controller
public class Secured {

    private final ObjectMapper json;

    public Secured(ObjectMapper json) {
        this.json = json;
    }

    @GetMapping(value = "/__/b", produces = TEXT_HTML_VALUE)
    public String secret(UsernamePasswordAuthenticationToken p, Model model) throws JsonProcessingException {
        model.addAttribute("token", json.writerWithDefaultPrettyPrinter().writeValueAsString(p));
        return "secured";
    }
}
