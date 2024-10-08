package bookku;

import org.springframework.data.annotation.Id;

public record Owner(@Id int id, String ownerName, String hashedPassword) {
}
