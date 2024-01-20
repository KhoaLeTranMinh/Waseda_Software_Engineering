/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.reservation;

import java.util.Date;

import app.AppException;

/**
 * Form class for Reserve Room
 * 
 */
public class ReserveRoomForm {

	private ReserveRoomControl reserveRoomControl = new ReserveRoomControl();

	private Date stayingDate;

	private ReserveRoomControl getReserveRoomControl() {
		return reserveRoomControl;
	}

	public String submitReservation() throws AppException {
		ReserveRoomControl reserveRoomControl = getReserveRoomControl();
		return reserveRoomControl.makeReservation(stayingDate);
	}

	public void cancelReservation(String reservationNumber) throws AppException {
		ReserveRoomControl reserveRoomControl = getReserveRoomControl();
		reserveRoomControl.cancelReservationAndIncreaseAvailability(reservationNumber);
	}

	public Date getStayingDate() {
		return stayingDate;
	}

	public void setStayingDate(Date stayingDate) {
		this.stayingDate = stayingDate;
	}

}
