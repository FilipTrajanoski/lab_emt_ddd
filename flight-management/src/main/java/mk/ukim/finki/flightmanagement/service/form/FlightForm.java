package mk.ukim.finki.flightmanagement.service.form;

import lombok.Data;
import mk.ukim.finki.flightmanagement.domain.model.FlightStatus;
import mk.ukim.finki.flightmanagement.domain.valueobjects.AdministratorUsername;
import mk.ukim.finki.flightmanagement.domain.valueobjects.AvailableSeats;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class FlightForm {

    @NotNull
    private String destination;

    @NotNull
    private String departurePoint;

    @NotNull
    private AvailableSeats availableSeats;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    private AdministratorUsername administrator;

    private FlightStatus status;

}
