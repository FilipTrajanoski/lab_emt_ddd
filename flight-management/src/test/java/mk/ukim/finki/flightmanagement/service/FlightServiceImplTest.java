package mk.ukim.finki.flightmanagement.service;

import mk.ukim.finki.flightmanagement.domain.exceptions.FlightIdNotFoundException;
import mk.ukim.finki.flightmanagement.domain.exceptions.UnavailableNumberOfSeatsException;
import mk.ukim.finki.flightmanagement.domain.model.Flight;
import mk.ukim.finki.flightmanagement.domain.model.FlightId;
import mk.ukim.finki.flightmanagement.domain.model.FlightStatus;
import mk.ukim.finki.flightmanagement.domain.valueobjects.*;
import mk.ukim.finki.flightmanagement.service.form.BookingForm;
import mk.ukim.finki.flightmanagement.service.form.FlightForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FlightServiceImplTest {

    @Autowired
    private FlightService flightService;

    Flight flight;

    @BeforeEach
    public void setup() {
        FlightForm flightForm = getFlightForm();
        FlightId flightId = flightService.createFlight(flightForm);
        flight = flightService.findById(flightId).orElseThrow(FlightIdNotFoundException::new);
    }

    private static FlightForm getFlightForm() {
        FlightForm flightForm = new FlightForm();
        flightForm.setDestination("Paris");
        flightForm.setDeparturePoint("Skopje");
        flightForm.setAvailableSeats(new AvailableSeats(100));
        flightForm.setDepartureTime(LocalDateTime.of(2024, 10, 3, 10, 30));
        flightForm.setArrivalTime(LocalDateTime.of(2024, 10, 3, 14, 0));
        flightForm.setAdministrator(new AdministratorUsername("admin"));
        return flightForm;
    }

    private static BookingForm getBookingForm() {
        BookingForm bookingForm = new BookingForm();
        bookingForm.setPassenger(new Passenger(PassengerId.randomId(PassengerId.class),
                "F", "T", "123"));
        bookingForm.setQuantity(new Quantity(50));

        return bookingForm;
    }

    @Test
    public void testCreateFlight() {
//        FlightForm flightForm = getFlightForm();
//
//        FlightId flightId = flightService.createFlight(flightForm);
//        Flight flight = flightService.findById(flightId).orElseThrow(FlightIdNotFoundException::new);
        assertNotNull(flight);
    }

    @Test
    public void testUpdateFlight() {
        FlightForm flightForm = getFlightForm();

        FlightId flightId = flightService.createFlight(flightForm);
        Flight flight = flightService.findById(flightId).orElseThrow(FlightIdNotFoundException::new);

        flightForm.setStatus(FlightStatus.CANCELLED);

        flight = flightService.updateFlight(flight.getId(), flightForm).orElseThrow();

        assertEquals(flightId, flight.getId());
        assertEquals(FlightStatus.CANCELLED, flight.getStatus());
    }


    @Test
    public void testRemoveFlight() {

        int sizeBeforeDeletion = flightService.findAll().size();

        if (sizeBeforeDeletion > 0) {
            flightService.removeFlight(flightService.findAll().get(0).getId());

            int sizeAfterDeletion = flightService.findAll().size();

            assertEquals(sizeBeforeDeletion - 1, sizeAfterDeletion);
        }

    }

    @Test
    public void testAddBooking() {
        BookingForm bookingForm = getBookingForm();

        int availableSeatsBeforeBooking = flight.getAvailableSeats().getSeats();

        flightService.addBooking(flight.getId(), bookingForm);
        flight = flightService.findById(flight.getId()).orElseThrow(FlightIdNotFoundException::new);

        assertEquals(availableSeatsBeforeBooking - 50, flight.getAvailableSeats().getSeats());

        flightService.removeBooking(flight.getId(),
                flight.getBookings().stream().toList().get(0).getId());

        flight = flightService.findById(flight.getId()).orElseThrow(FlightIdNotFoundException::new);

        assertEquals(availableSeatsBeforeBooking, flight.getAvailableSeats().getSeats());
    }

    @Test
    public void testAddBooking2() {
        BookingForm bookingForm = getBookingForm();
        bookingForm.setQuantity(new Quantity(110));

        assertThrows(UnavailableNumberOfSeatsException.class, () -> flightService.addBooking(flight.getId(), bookingForm));
    }

    @Test
    public void testAddBooking3() {
        BookingForm bookingForm = getBookingForm();
        bookingForm.setQuantity(new Quantity(100));

        flightService.addBooking(flight.getId(), bookingForm);

        flight = flightService.findById(flight.getId()).orElseThrow(FlightIdNotFoundException::new);

        assertEquals(FlightStatus.FULL, flight.getStatus());
    }
}
