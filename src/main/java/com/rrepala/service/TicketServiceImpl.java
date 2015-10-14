package com.rrepala.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.rrepala.bo.Level;
import com.rrepala.bo.Seat;
import com.rrepala.bo.SeatHold;

/**
 * @author Ramakrishna
 *
 */
public class TicketServiceImpl implements TicketService {

	private static Map<Integer, SeatHold> onHoldSeats = new ConcurrentHashMap<Integer, SeatHold>();
	private static Map<String, List<Seat>> reservedSeats = new ConcurrentHashMap<String, List<Seat>>();
	private static Map<Level, List<Seat>> availableSeats = new ConcurrentHashMap<Level, List<Seat>>();
	private static AtomicInteger seatHoldId = new AtomicInteger(1);
	private static final long timoutInterval = 5000;
	private static List<Level> sortedLevels = Arrays.asList(Level.values());

	static {
		for (Level level : Level.values()) {
			for (int i = 1; i <= level.getNumOfRows(); i++) {
				for (int j = 1; j <= level.getNumOfSeats(); j++) {
					if (availableSeats.get(level) == null) {
						availableSeats.put(level, new ArrayList<Seat>());
					}

					availableSeats.get(level).add(new Seat(j, i));
				}
			}
		}

		Collections.sort(sortedLevels, new Comparator<Level>() {
			@Override
			public int compare(Level level1, Level level2) {
				return level1.getRank() - level2.getRank();
			}
		});
	}

	@Override
	public int numSeatsAvailable() {
		invalidateOnHoldSeats();
		int numberOfSeats = 0;
		for (Level level : Level.values()) {
			numberOfSeats += availableSeats.get(level).size();
		}

		return numberOfSeats;
	}

	@Override
	public int numSeatsAvailable(int venueLevel) {
		invalidateOnHoldSeats();
		int numberOfSeats = 0;
		for (Level level : Level.values()) {
			if (level.getRank() == venueLevel)
				numberOfSeats += availableSeats.get(level).size();
		}
		return numberOfSeats;
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, int minLevel, int maxLevel, String customerEmail) {
		invalidateOnHoldSeats();
		SeatHold seatHoldInfo = new SeatHold(seatHoldId.getAndIncrement());

		for (Level level : sortedLevels) {
			if (level.getRank() >= minLevel && level.getRank() <= maxLevel) {
				if (availableSeats.get(level).size() >= numSeats) {
					List<Seat> seats = new ArrayList<Seat>(availableSeats.get(level).subList(0, numSeats));
					availableSeats.get(level).subList(0, numSeats).clear();
					seatHoldInfo.setSeats(seats);
					seatHoldInfo.setHoldTime(new Date());
					seatHoldInfo.setLevel(level);
					onHoldSeats.put(seatHoldInfo.getId(), seatHoldInfo);
					break;
				}
			}
		}

		return seatHoldInfo;
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		int minLevel = Integer.MAX_VALUE;
		int maxLevel = Integer.MIN_VALUE;

		for (Level level : Level.values()) {
			if (level.getRank() > maxLevel) {
				maxLevel = level.getRank();
			}

			if (level.getRank() < minLevel) {
				minLevel = level.getRank();
			}
		}

		return findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, int leveSelected, String customerEmail) {
		return findAndHoldSeats(numSeats, leveSelected, leveSelected, customerEmail);
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		String confirmationCode = "No seats are Reserved";
		invalidateOnHoldSeats();
		SeatHold seatHoldInfo = onHoldSeats.get(seatHoldId);

		if (seatHoldInfo != null) {
			List<Seat> seats = seatHoldInfo.getSeats();
			confirmationCode = UUID.randomUUID().toString();
			reservedSeats.put(customerEmail + confirmationCode, seats);
			onHoldSeats.remove(seatHoldId);
		}

		return confirmationCode;
	}

	private void invalidateOnHoldSeats() {
		Iterator<Entry<Integer, SeatHold>> iterator = onHoldSeats.entrySet().iterator();
		Date date = new Date();

		while (iterator.hasNext()) {
			Entry<Integer, SeatHold> entry = iterator.next();
			SeatHold seatHoldInfo = entry.getValue();

			if (date.getTime() - seatHoldInfo.getHoldTime().getTime() > timoutInterval) {
				availableSeats.get(seatHoldInfo.getLevel()).addAll(seatHoldInfo.getSeats());
				iterator.remove();
			}
		}
	}

	/*
	 * // To display the results in console.
	 * 
	 * public static void printData() { log("\nAvailable Seats"); for (Level
	 * level : Level.values()) { System.out.print("Level ==> " +
	 * level.getName()); for (Seat seat : availableSeats.get(level)) {
	 * System.out.print("[" + seat.getRowNumber() + "," + seat.getSeatNumber() +
	 * "]"); } System.out.println(); }
	 * 
	 * log("\nReserved Seats"); for (String key : reservedSeats.keySet()) { for
	 * (Seat seat : reservedSeats.get(key)) { System.out.print("[" +
	 * seat.getRowNumber() + "," + seat.getSeatNumber() + "]"); }
	 * System.out.println(); }
	 * 
	 * log("\nOn Hold Seats"); for (Entry<Integer, SeatHold> entry :
	 * onHoldSeats.entrySet()) { SeatHold seatHoldInfo =
	 * onHoldSeats.get(entry.getKey()); log("SeatHoldID ==> " +
	 * seatHoldInfo.getId()); log("Level ==> " + seatHoldInfo.getLevel()); for
	 * (Seat seat : seatHoldInfo.getSeats()) { System.out.print("[" +
	 * seat.getRowNumber() + "," + seat.getSeatNumber() + "]"); }
	 * System.out.println(); }
	 * 
	 * log("==============================="); System.out.println(); }
	 * 
	 * private static void log(String msg) { System.out.println(msg); }
	 */
}
