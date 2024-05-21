package mk.ukim.finki.flightmanagement.domain.valueobjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.DomainObjectId;

public class PassengerId extends DomainObjectId {

    private PassengerId() {
        super(PassengerId.randomId(PassengerId.class).getId());
    }

    public PassengerId(@NonNull String uuid) {
        super(uuid);
    }
}
