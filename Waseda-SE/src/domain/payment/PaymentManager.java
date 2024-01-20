/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.payment;

import java.util.Scanner;
import java.util.Date;

import util.DateUtil;
import domain.DaoFactory;

/**
 * Manager for payments<br>
 * 
 */
public class PaymentManager {

	/**
	 * Fee per one night<br>
	 */
	private static final int RATE_PER_DAY = 8000;

	public void createPayment(Date stayingDate, String roomNumber) throws PaymentException,
			NullPointerException {
		if (stayingDate == null) {
			throw new NullPointerException("stayingDate");
		}
		if (roomNumber == null) {
			throw new NullPointerException("roomNumber");
		}

		Payment payment = new Payment();
		payment.setStayingDate(stayingDate);
		payment.setRoomNumber(roomNumber);
		payment.setAmount(getRatePerDay(roomNumber));
		payment.setStatus(Payment.PAYMENT_STATUS_CREATE);

		PaymentDao paymentDao = getPaymentDao();
		paymentDao.createPayment(payment);
	}

	private int getRatePerDay(String roomNumber) {
		return RATE_PER_DAY;
	}

	public void consumePayment(Date stayingDate, String roomNumber) throws PaymentException,
			NullPointerException {
		if (stayingDate == null) {
			throw new NullPointerException("stayingDate");
		}
		if (roomNumber == null) {
			throw new NullPointerException("roomNumber");
		}

		PaymentDao paymentDao = getPaymentDao();
		Payment payment = paymentDao.getPayment(stayingDate, roomNumber);// by using sql query, get the already created
																			// payment in the checkin process
		// If corresponding payment does not exist
		if (payment == null) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_PAYMENT_NOT_FOUND);
			exception.getDetailMessages().add("staying_date[" + DateUtil.convertToString(stayingDate) + "]");
			exception.getDetailMessages().add("room_number[" + roomNumber + "]");
			throw exception;
		}
		// If payment has been consumed already
		if (payment.getStatus().equals(Payment.PAYMENT_STATUS_CONSUME)) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_PAYMENT_ALREADY_CONSUMED);
			exception.getDetailMessages().add("staying_date[" + DateUtil.convertToString(stayingDate) + "]");
			exception.getDetailMessages().add("room_number[" + roomNumber + "]");
			throw exception;
		}

		System.out.println("Amount to pay: " + payment.getAmount());
		System.out.print("> ");
		System.out.println("Input the money amount");
		System.out.print("> ");
		Scanner scan = new Scanner(System.in);
		int moneyPaid = scan.nextInt();

		while (moneyPaid < payment.getAmount()) {
			payment.setAmount(payment.getAmount() - moneyPaid);
			System.out.println("staying_date[" + DateUtil.convertToString(stayingDate) + "]");
			System.out.println("room_number[" + roomNumber + "]");
			System.out.println("Amount left to pay: " + payment.getAmount());
			System.out.println("Input the money amount");
			System.out.print("> ");
			moneyPaid = scan.nextInt();
		}
		if (moneyPaid > payment.getAmount()) {
			System.out.println(
					"Here is your change: " + (moneyPaid - payment.getAmount()) + "\n Thank you for your stay!");

		}

		// scan.close();
		payment.setStatus(Payment.PAYMENT_STATUS_CONSUME);
		paymentDao.updatePayment(payment);
	}

	private PaymentDao getPaymentDao() {
		return DaoFactory.getInstance().getPaymentDao();
	}
}
