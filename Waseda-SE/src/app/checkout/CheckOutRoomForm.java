/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.checkout;

import app.AppException;
import app.checkin.CheckInRoomControl;

/**
 * Form class for Check-out Customer
 * 
 */
public class CheckOutRoomForm {

	private CheckOutRoomControl checkOutRoomControl = new CheckOutRoomControl();

	private String roomNumber;

	private CheckOutRoomControl getCheckOutRoomControl() {
		return checkOutRoomControl;
	}

	public void checkOut() throws AppException {
		/**
		 * Your code for conducting check-out by using some Control object
		 */
		CheckOutRoomControl checkOutRoomControl = getCheckOutRoomControl();
		checkOutRoomControl.checkOut(roomNumber);
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

}
