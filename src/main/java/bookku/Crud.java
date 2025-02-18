package bookku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Controller
@RequestMapping("/__")
public class Crud {

    private static final Logger log = LoggerFactory.getLogger(Crud.class);

    private final Bookmarks bookmarks;

    public Crud(Bookmarks bookmarks) {
        this.bookmarks = bookmarks;
    }

    @GetMapping
    public String form(UriComponentsBuilder builder, Model model) {
        model.addAttribute("redirect", builder.cloneBuilder().path("b/%s").build());
        model.addAttribute(
                "bookmarklet",
                String.format(
                        "javascript:window.location='%s?target='+window.btoa(window.location).replaceAll('+','-').replaceAll('/','_');",
                        builder.cloneBuilder().path("__").build()));
        return "form";
    }

    @PostMapping(consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String create(Slug slug, Target target) {
        log.info("Form submitted: '{}' -> '{}'", slug.value(), target.value());
        bookmarks.create(slug, target);
        return String.format("redirect:%s", target.value());
    }
}
