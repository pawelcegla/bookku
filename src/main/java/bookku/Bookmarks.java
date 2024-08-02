package bookku;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface Bookmarks extends ListCrudRepository<Bookmark, Integer> {

    Optional<Bookmark> findByHasz(String hasz);
}
