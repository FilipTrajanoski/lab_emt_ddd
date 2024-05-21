package mk.ukim.finki.flightmanagement.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.flightmanagement.domain.valueobjects.AdministratorUsername;
import mk.ukim.finki.flightmanagement.domain.valueobjects.AvailableSeats;
import mk.ukim.finki.flightmanagement.service.form.FlightForm;
import mk.ukim.finki.emt.sharedkernel.domain.AbstractEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "flight")
@Getter
public class Flight extends AbstractEntity<FlightId> {

    private String destination;

    private String departurePoint;

    private AvailableSeats availableSeats;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private AdministratorUsername administrator;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Booking> bookings;

    protected Flight(){
        super(FlightId.randomId(FlightId.class));
    }

    public Flight(String destination,
                  String departurePoint,
                  AvailableSeats availableSeats,
                  LocalDateTime departureTime,
                  LocalDateTime arrivalTime,
                  AdministratorUsername administrator) {
        super(FlightId.randomId(FlightId.class));
        this.destination = destination;
        this.departurePoint = departurePoint;
        this.availableSeats = availableSeats;
        this.status = FlightStatus.AVAILABLE;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.bookings = new HashSet<>();
        this.administrator = administrator;
    }

    public Booking addBooking(@NonNull Booking booking){
        Objects.requireNonNull(booking, "booking must not be null");
        bookings.add(booking);
        this.availableSeats = this.availableSeats.decreaseAvailableSeats(booking.getQuantity());
        return booking;
    }

    public Optional<Booking> removeBooking(@NonNull BookingId bookingId) {
        Objects.requireNonNull(bookingId, "bookingId must not be null");

        Optional<Booking> booking = bookings.stream()
                .filter(b -> b.getId().equals(bookingId))
                .findFirst();

        booking.ifPresent(b -> {
            bookings.remove(b);
            this.availableSeats = this.availableSeats.increaseAvailableSeats(b.getQuantity());
        });

        return booking;
    }


    public void checkAvailableSeats(){
        if (this.getAvailableSeats().getSeats() == 0)
            this.status = FlightStatus.FULL;
        else
            this.status = FlightStatus.AVAILABLE;
    }

    public void updateFlight(FlightForm flightForm){
        this.destination = flightForm.getDestination();
        this.departurePoint = flightForm.getDeparturePoint();
        this.availableSeats = flightForm.getAvailableSeats();
        this.departureTime = flightForm.getDepartureTime();
        this.arrivalTime = flightForm.getArrivalTime();
        this.administrator = flightForm.getAdministrator();
        this.status = flightForm.getStatus();
    }
}
