package bookku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Controller
@RequestMapping("/__/b")
public class Secured {

    private static final Logger log = LoggerFactory.getLogger(Secured.class);

    @GetMapping(produces = TEXT_HTML_VALUE)
    public String secret() {
        return "secured_form";
    }

    @PostMapping(consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String create(Slug slug, Target target) {
        log.info("Form submitted: '{}' -> '{}'", slug.value(), target.value());
        return String.format("redirect:%s", target.value());
    }
}
