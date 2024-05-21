package mk.ukim.finki.flightmanagement.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.ValueObject;

@Embeddable
@Getter
public class AdministratorUsername implements ValueObject {

    private final String username;

    public AdministratorUsername(String username) {
        this.username = username;
    }

    protected AdministratorUsername(){
        this.username = "";
    }
}
