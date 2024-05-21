package mk.ukim.finki.flightmanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.flightmanagement.domain.exceptions.BookingIdNotFoundException;
import mk.ukim.finki.flightmanagement.domain.exceptions.FlightIdNotFoundException;
import mk.ukim.finki.flightmanagement.domain.exceptions.UnavailableNumberOfSeatsException;
import mk.ukim.finki.flightmanagement.domain.model.Booking;
import mk.ukim.finki.flightmanagement.domain.model.BookingId;
import mk.ukim.finki.flightmanagement.domain.model.Flight;
import mk.ukim.finki.flightmanagement.domain.model.FlightId;
import mk.ukim.finki.flightmanagement.domain.repository.FlightRepository;
import mk.ukim.finki.flightmanagement.service.FlightService;
import mk.ukim.finki.flightmanagement.service.form.BookingForm;
import mk.ukim.finki.flightmanagement.service.form.FlightForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final Validator validator;

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public FlightId createFlight(FlightForm flightForm) {
        Objects.requireNonNull(flightForm, "flight must not be null");
        var constraintViolations = validator.validate(flightForm);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException("The flight form is not valid", constraintViolations);
        }

        var newFlight = flightRepository.saveAndFlush(toDomainObject(flightForm));

        return newFlight.getId();
    }

    @Override
    public Optional<Flight> updateFlight(FlightId flightId, FlightForm flightForm) {
        var flight = this.findById(flightId).orElseThrow(FlightIdNotFoundException::new);

        Objects.requireNonNull(flightForm, "flight must not be null");
        var constraintViolations = validator.validate(flightForm);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException("The flight form is not valid", constraintViolations);
        }

        flight.updateFlight(flightForm);
        flightRepository.saveAndFlush(flight);

        return Optional.of(flight);
    }

    @Override
    public void removeFlight(FlightId flightId) {
        this.findById(flightId).orElseThrow(FlightIdNotFoundException::new);
        flightRepository.deleteById(flightId);
    }

    @Override
    public Optional<Flight> findById(FlightId flightId) {
        return flightRepository.findById(flightId);
    }

    @Override
    public void addBooking(FlightId flightId, BookingForm bookingForm) throws FlightIdNotFoundException {
        Flight flight = this.findById(flightId).orElseThrow(FlightIdNotFoundException::new);

        Objects.requireNonNull(bookingForm, "booking must not be null");
        var constraintViolations = validator.validate(bookingForm);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException("The booking form is not valid", constraintViolations);
        }

        if (flight.getAvailableSeats().getSeats() >= bookingForm.getQuantity().getQuantity()) {
            flight.addBooking(toDomainObjectBooking(bookingForm));
            flight.checkAvailableSeats();
            flightRepository.saveAndFlush(flight);
        } else {
            throw new UnavailableNumberOfSeatsException();
        }

    }

    @Override
    public void removeBooking(FlightId flightId, BookingId bookingId) throws FlightIdNotFoundException, BookingIdNotFoundException {
        Flight flight = this.findById(flightId).orElseThrow(FlightIdNotFoundException::new);
        flight.removeBooking(bookingId);
        flight.checkAvailableSeats();
        flightRepository.saveAndFlush(flight);
    }

    private Flight toDomainObject(FlightForm flightForm) {
        return new Flight(flightForm.getDestination(),
                flightForm.getDeparturePoint(),
                flightForm.getAvailableSeats(),
                flightForm.getDepartureTime(),
                flightForm.getArrivalTime(),
                flightForm.getAdministrator());
    }

    private Booking toDomainObjectBooking(BookingForm bookingForm) {
        return new Booking(bookingForm.getQuantity(), bookingForm.getPassenger().getPassengerId());
    }
}
