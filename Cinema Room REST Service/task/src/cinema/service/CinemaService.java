package cinema.service;

import cinema.exception.PasswordIsIncorrectException;
import cinema.exception.SeatBoundsException;
import cinema.exception.TicketPurchaseException;
import cinema.exception.TokenIsIncorrectException;
import cinema.model.*;

import java.util.HashMap;
import java.util.Map;

public class CinemaService {

    private final Statistics statistics;
    private final Cinema cinema;
    private final CinemaBuild cinemaBuild;
    private final Map<String, Ticket> tickets = new HashMap<>();

    public CinemaService() {
        cinemaBuild = new CinemaBuild();
        cinema = cinemaBuild.cinemaBuilder();
        statistics = new Statistics(0, 81, 0);
    }

    public Cinema getSeats() {
        return cinema;
    }

    public Ticket buyTicket(Seat seat) {
        seat.setPrice();
        if (cinema.getReservedSeats().size() != 0 && cinema.getReservedSeats().contains(seat)) {
            throw new TicketPurchaseException();

        } else if (seat.getRow() > 9 || seat.getRow() < 1 || (seat.getColumn() > 9 || seat.getColumn() < 1)) {
            throw new SeatBoundsException();

        } else {
            cinemaBuild.addToReservedSeats(seat);
            cinemaBuild.deleteFromAvailableSeats(seat);
            Ticket ticket = new Ticket(seat);
            tickets.put(ticket.getToken(), ticket);
            updateStatistics(seat, true);
            return ticket;
        }
    }

    public ReturnedTicket returnTicket(Token token) {
        if (tickets.containsKey(token.getToken())) {
            Ticket ticket = tickets.get(token.getToken());
            tickets.remove(ticket.getToken());
            cinemaBuild.deleteFromReservedSeats(ticket.getSeat());
            cinemaBuild.addToAvailableSeats(ticket.getSeat());
            updateStatistics(ticket.getSeat(), false);
            return new ReturnedTicket(ticket.getSeat());

        } else {
            throw new TokenIsIncorrectException();
        }

    }

    public Statistics getStatistics(String password) {
        if ("super_secret".equals(password)) {
            return statistics;
        } else {
            throw new PasswordIsIncorrectException();
        }

    }

    private void updateStatistics(Seat seat, boolean plusIncome) {
        if (plusIncome) {
            statistics.setCurrentIncome(statistics.getCurrentIncome() + seat.getPrice());

        } else {
            statistics.setCurrentIncome(statistics.getCurrentIncome() - seat.getPrice());

        }
        statistics.setNumberOfPurchasedTickets(tickets.size());
        statistics.setNumberOfAvailableSeats(cinema.getAvailableSeats().size());
    }
}
