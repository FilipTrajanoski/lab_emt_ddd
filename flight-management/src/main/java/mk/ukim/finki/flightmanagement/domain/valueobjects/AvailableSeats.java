package mk.ukim.finki.flightmanagement.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.ValueObject;

@Embeddable
@Getter
public class AvailableSeats implements ValueObject {

    private final int seats;

    public AvailableSeats(int seats) {
        this.seats = seats;
    }

    protected AvailableSeats() {
        this.seats = 0;
    }

    public AvailableSeats decreaseAvailableSeats(Quantity quantity){
        return new AvailableSeats(this.seats - quantity.getQuantity());
    }

    public AvailableSeats increaseAvailableSeats(Quantity quantity){
        return new AvailableSeats(this.seats + quantity.getQuantity());
    }
}
