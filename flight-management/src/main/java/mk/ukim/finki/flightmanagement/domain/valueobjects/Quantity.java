package mk.ukim.finki.flightmanagement.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.ValueObject;

@Embeddable
@Getter
public class Quantity implements ValueObject {

    private final int quantity;

    protected Quantity(){
        this.quantity = 0;
    }

    public Quantity(int quantity){
        this.quantity = quantity;
    }
}
