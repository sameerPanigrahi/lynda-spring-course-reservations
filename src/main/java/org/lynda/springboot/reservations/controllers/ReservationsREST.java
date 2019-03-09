package org.lynda.springboot.reservations.controllers;

import java.util.List;
import org.lynda.springboot.reservations.business.domain.RoomReservation;
import org.lynda.springboot.reservations.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ReservationsREST {

	@Autowired
	private ReservationService $reservationService;

	@RequestMapping(value = "reservations/{date}", method = RequestMethod.GET)
	public List<RoomReservation> getAllRoomReservationsByDate(
			@PathVariable(value = "date", required = false) String dateString) {
		List<RoomReservation> roomReservations = $reservationService.getAllRoomReservationsByDate(dateString);
		return roomReservations;
	}
}
