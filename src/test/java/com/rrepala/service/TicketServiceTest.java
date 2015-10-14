package com.rrepala.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.rrepala.bo.Level;
import com.rrepala.bo.SeatHold;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServiceTest {

	private TicketService ticketService;
	private int totalNumberOfSeats;
	private String email;

	@Before
	public void init() {
		ticketService = new TicketServiceImpl();
		email = "test@gmail.com";
		for (Level level : Level.values()) {
			totalNumberOfSeats += level.getNumOfRows() * level.getNumOfSeats();
		}
	}

	@Test
	public void getNumberOfSeatsAvailable() {
		int numberOfSeats = ticketService.numSeatsAvailable();
		assertEquals(totalNumberOfSeats, numberOfSeats);
	}
	
	@Test
	public void getSeatsNeitherHeldNorReserved() {
		int minLevel = 2, maxLevel = 3,numOfSeats = 5;
		ticketService.findAndHoldSeats(numOfSeats, minLevel, maxLevel, email);
		assertEquals(totalNumberOfSeats-numOfSeats, ticketService.numSeatsAvailable());
	}
	
	@Test
	public void seatsAvailableInMinLevel_BooksMinLevel() {
		int minLevel = 2, maxLevel = 3;
		SeatHold seatHoldInfo = ticketService.findAndHoldSeats(5, minLevel, maxLevel, email);
		assertEquals(minLevel, seatHoldInfo.getLevel().getRank());
	}
	

	@Test
	public void seatsRequiredNotAvailableInMinLevel_BooksFromNextLevel() {
		int minLevel = 2, maxLevel = 4;
		SeatHold seatHoldInfo = ticketService.findAndHoldSeats(1600, minLevel, maxLevel, email);
		ticketService.reserveSeats(seatHoldInfo.getId(), email);
		assertEquals(3, seatHoldInfo.getLevel().getRank());
	}

	@Test
	public void invalidateHoldSeats() {
		String expectedOutput = "No seats are Reserved";
		SeatHold seatHoldInfo = ticketService.findAndHoldSeats(5, 3, email);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String code = ticketService.reserveSeats(seatHoldInfo.getId(), email);
		assertEquals(expectedOutput, code);
	}

	@Test
	public void holdSeatsToReservedSeats() {
		String expectedOutput = "No seats are Reserved";
		SeatHold seatHoldInfo = ticketService.findAndHoldSeats(5, 2, 4, email);
		String code = ticketService.reserveSeats(seatHoldInfo.getId(), email);
		assertNotSame(expectedOutput, code);
	}
}
