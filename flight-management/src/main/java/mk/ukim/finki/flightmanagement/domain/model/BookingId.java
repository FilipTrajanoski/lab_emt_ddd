package mk.ukim.finki.flightmanagement.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.DomainObjectId;

public class BookingId extends DomainObjectId {

    private BookingId() {
        super(BookingId.randomId(BookingId.class).getId());
    }

    public BookingId(@NonNull String uuid) {
        super(uuid);
    }

    public boolean equals(BookingId obj) {
        return this.getId().equals(obj.getId());
    }
}
