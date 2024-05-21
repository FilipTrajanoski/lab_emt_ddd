package mk.ukim.finki.flightmanagement.service;

import mk.ukim.finki.flightmanagement.domain.exceptions.BookingIdNotFoundException;
import mk.ukim.finki.flightmanagement.domain.exceptions.FlightIdNotFoundException;
import mk.ukim.finki.flightmanagement.domain.model.BookingId;
import mk.ukim.finki.flightmanagement.domain.model.Flight;
import mk.ukim.finki.flightmanagement.domain.model.FlightId;
import mk.ukim.finki.flightmanagement.service.form.BookingForm;
import mk.ukim.finki.flightmanagement.service.form.FlightForm;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    List<Flight> findAll();

    FlightId createFlight(FlightForm flightForm);

    Optional<Flight> updateFlight(FlightId flightId, FlightForm flightForm) throws FlightIdNotFoundException;

    void removeFlight(FlightId flightId) throws FlightIdNotFoundException;

    Optional<Flight> findById(FlightId flightId);

    void addBooking(FlightId flightId, BookingForm bookingForm) throws FlightIdNotFoundException;

    void removeBooking(FlightId flightId, BookingId bookingId) throws FlightIdNotFoundException, BookingIdNotFoundException;

}
