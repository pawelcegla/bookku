package bookku;

import org.springframework.data.annotation.Id;

public record Bookmark(@Id int id, String hasz, String target) {
}
