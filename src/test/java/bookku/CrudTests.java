package bookku;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest
public class CrudTests {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    Bookmarks bookmarks;

    @Test
    @WithMockUser
    void successfulFormSubmissionShouldRedirectToTarget() throws Exception {
        mvc.perform(
                post("/__")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("slug", "ex")
                        .param("target", "https://example.org")
                        .with(csrf())
        ).andExpect(redirectedUrl("https://example.org"));
    }
}
