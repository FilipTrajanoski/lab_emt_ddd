package mk.ukim.finki.flightmanagement.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.flightmanagement.domain.valueobjects.PassengerId;
import mk.ukim.finki.flightmanagement.domain.valueobjects.Quantity;
import mk.ukim.finki.emt.sharedkernel.domain.AbstractEntity;

@Entity
@Table(name = "booking")
@Getter
public class Booking extends AbstractEntity<BookingId> {

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    //    private Long seatNumber;
    private Quantity quantity;

    @AttributeOverride(name = "id", column = @Column(name = "passenger_id", nullable = false))
    private PassengerId passengerId;

    protected Booking() {
        super(BookingId.randomId(BookingId.class));
    }

    public Booking(@NonNull Quantity quantity, @NonNull PassengerId passengerId) {
        super(BookingId.randomId(BookingId.class));
        this.status = BookingStatus.CONFIRMED;
        this.quantity = quantity;
        this.passengerId = passengerId;
    }
}
