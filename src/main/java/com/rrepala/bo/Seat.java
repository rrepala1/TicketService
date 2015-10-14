package com.rrepala.bo;

public class Seat {

	private int seatNumber;
	private int rowNumber;

	public Seat(int seatNumber, int rowNumber) {
		this.seatNumber = seatNumber;
		this.rowNumber = rowNumber;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
}
