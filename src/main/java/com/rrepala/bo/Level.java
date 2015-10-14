package com.rrepala.bo;

/**
 * @author Ramakrishna
 *
 */
public enum Level {
	ORCHESTRA("Orchestra", 100, 25, 50, 4), MAIN("Main", 75, 20, 100, 3), BALCONY("BALCONY", 50, 15, 100,
			2), GROUND("GROUND", 40, 15, 100, 1);

	private String name;
	private double price;
	private int numOfRows;
	private int numOfSeats;
	private int rank;

	Level(String name, double price, int numOfRows, int numOfSeats, int rank) {
		this.name = name;
		this.price = price;
		this.numOfRows = numOfRows;
		this.numOfSeats = numOfSeats;
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public int getNumOfSeats() {
		return numOfSeats;
	}

	public int getRank() {
		return rank;
	}
}
