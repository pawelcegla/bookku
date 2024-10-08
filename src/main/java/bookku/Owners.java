package bookku;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface Owners extends ListCrudRepository<Owner, Integer> {

    Optional<Owner> findByOwnerName(String ownerName);
}
