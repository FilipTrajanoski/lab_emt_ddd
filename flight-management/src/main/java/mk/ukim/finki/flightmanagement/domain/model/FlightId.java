package mk.ukim.finki.flightmanagement.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.DomainObjectId;

public class FlightId extends DomainObjectId {
    private FlightId() {
        super(FlightId.randomId(FlightId.class).getId());
    }

    public FlightId(@NonNull String uuid) {
        super(uuid);
    }

}
