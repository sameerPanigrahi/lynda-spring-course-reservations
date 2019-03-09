package org.lynda.springboot.reservations.controllers;

import java.util.List;
import org.lynda.springboot.reservations.business.domain.RoomReservation;
import org.lynda.springboot.reservations.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {

	@Autowired
	private ReservationService $reservationService;

	@RequestMapping(method = RequestMethod.GET)
	// @ResponseBody
	public String getReservations(@RequestParam(value = "date", required = false) String dateString, Model model) {

		List<RoomReservation> roomReservationList = $reservationService.getAllRoomReservationsByDate(dateString);

		model.addAttribute("roomReservations", roomReservationList);
		return "reservations";// return the name of the template html you want
								// to display
	}

}
