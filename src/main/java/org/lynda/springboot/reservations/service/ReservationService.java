package org.lynda.springboot.reservations.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lynda.springboot.reservations.business.domain.RoomReservation;
import org.lynda.springboot.reservations.entity.*;
import org.lynda.springboot.reservations.repository.GuestRepository;
import org.lynda.springboot.reservations.repository.ReservationRepository;
import org.lynda.springboot.reservations.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

	private RoomRepository roomRepository;
	private GuestRepository guestRepository;
	private ReservationRepository reservationRepository;

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
			ReservationRepository reservationRepository) {
		super();
		this.roomRepository = roomRepository;
		this.guestRepository = guestRepository;
		this.reservationRepository = reservationRepository;
	}

	public List<RoomReservation> getAllRoomReservationsByDate(String dateString) {

		Date date = createDateFromString(dateString);

		Map<Long, RoomReservation> roomReservationMap = new HashMap<>();

		Iterable<Room> rooms = this.roomRepository.findAll();
		rooms.forEach(roomTuple -> {
			RoomReservation roomReservation = new RoomReservation();
			roomReservation.setRoomId(roomTuple.getId());
			roomReservation.setRoomName(roomTuple.getName());
			roomReservation.setRoomNumber(roomTuple.getNumber());
			roomReservationMap.put(roomReservation.getRoomId(), roomReservation);
		});

		Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
		if (reservations != null) {
			reservations.forEach(reservationTuple -> {
				Guest guest = this.guestRepository.findOne(reservationTuple.getGuestId());
				if (guest != null) {
					RoomReservation roomReservation = roomReservationMap.get(reservationTuple.getRoomId());
					roomReservation.setGuestId(guest.getId());
					roomReservation.setFirstName(guest.getFirstName());
					roomReservation.setLastName(guest.getLastName());
					roomReservation.setDate(reservationTuple.getDate());
				}

			});
		}

		List<RoomReservation> allRoomReservations = new ArrayList<RoomReservation>();

		for (Long roomId : roomReservationMap.keySet()) {
			allRoomReservations.add(roomReservationMap.get(roomId));
		}

		return allRoomReservations;
	}

	private Date createDateFromString(String dateString) {
		Date date;
		if (dateString != null) {
			try {
				date = dateFormat.parse(dateString);
			} catch (ParseException exe) {
				date = new Date();
			}

		} else {
			date = new Date();
		}

		return date;
	}

}
