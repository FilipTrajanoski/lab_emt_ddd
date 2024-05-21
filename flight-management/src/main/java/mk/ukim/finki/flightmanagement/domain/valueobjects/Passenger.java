package mk.ukim.finki.flightmanagement.domain.valueobjects;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.ValueObject;

@Getter
public class Passenger implements ValueObject {

    private final PassengerId passengerId;
    private final String name;
    private final String surname;
    private final String phone;

    private Passenger(){
        this.passengerId = PassengerId.randomId(PassengerId.class);
        this.name = "";
        this.surname = "";
        this.phone = "";
    }

    public Passenger(PassengerId passengerId, String name, String surname, String phone) {
        this.passengerId = passengerId;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }
}
