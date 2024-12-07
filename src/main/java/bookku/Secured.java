package bookku;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Controller
@RequestMapping("/__/b")
public class Secured {

    @GetMapping(produces = TEXT_HTML_VALUE)
    public String secret() {
        return "secured";
    }

    @PostMapping(consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public String create(Slug slug, Target target) {
        return String.format("%s -> %s", slug, target);
    }
}
