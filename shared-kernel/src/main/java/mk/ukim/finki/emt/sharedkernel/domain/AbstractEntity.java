package mk.ukim.finki.emt.sharedkernel.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@MappedSuperclass
@Getter
public class AbstractEntity <ID extends DomainObjectId>{

    @EmbeddedId
    private ID id;

    protected AbstractEntity(@NonNull ID id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

}
