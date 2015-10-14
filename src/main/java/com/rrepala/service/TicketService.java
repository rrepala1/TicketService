package com.rrepala.service;

import com.rrepala.bo.SeatHold;

public interface TicketService {

	int numSeatsAvailable(int venueLevel);

	int numSeatsAvailable();

	SeatHold findAndHoldSeats(int numSeats, String customerEmail);

	SeatHold findAndHoldSeats(int numSeats, int leveSelected, String customerEmail);

	SeatHold findAndHoldSeats(int numSeats, int minLevel, int maxLevel, String customerEmail);

	String reserveSeats(int seatHoldId, String customerEmail);

}
