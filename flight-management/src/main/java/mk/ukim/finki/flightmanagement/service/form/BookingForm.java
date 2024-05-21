package mk.ukim.finki.flightmanagement.service.form;

import lombok.Data;
import mk.ukim.finki.flightmanagement.domain.valueobjects.Passenger;
import mk.ukim.finki.flightmanagement.domain.valueobjects.Quantity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class BookingForm {

    //    @Min(1)
    @NotNull
    private Quantity quantity;

    @NotNull
    private Passenger passenger;

}
