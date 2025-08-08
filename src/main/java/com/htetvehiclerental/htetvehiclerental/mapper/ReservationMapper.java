package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;

public class ReservationMapper {
    public static ReservationDto mapToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReservation_id(reservation.getReservation_id());
        reservationDto.setVehicle(reservation.getVehicle().getVehicle_id());
        reservationDto.setVehicleBrand(reservation.getVehicle().getBrand());
        reservationDto.setVehicleModel(reservation.getVehicle().getModel());
        reservationDto.setMake_year(reservation.getVehicle().getMake_year());
        reservationDto.setImage_url(reservation.getVehicle().getImage_url());
        reservationDto.setCustomer(reservation.getCustomer().getCustomer_id());
        reservationDto.setPick_up_date(reservation.getPick_up_date());
        reservationDto.setDrop_off_date(reservation.getDrop_off_date());
        reservationDto.setActual_pick_up_date(reservation.getActual_pick_up_date());
        reservationDto.setActual_drop_off_date(reservation.getActual_drop_off_date());
        reservationDto.setBaby_seat(reservation.getBaby_seat());
        reservationDto.setInsurance(reservation.getInsurance());
        reservationDto.setDamages(reservation.getDamages());
        reservationDto.setLate_fee(reservation.getLate_fee());
        reservationDto.setTotal_charge(reservation.getTotal_charge());
        reservationDto.setRes_status(reservation.getRes_status());
        reservationDto.setRes_created_date(reservation.getRes_created_date());
        reservationDto.setRes_modified_date(reservation.getRes_modified_date());
        reservationDto.setRes_last_action(reservation.getRes_last_action());
        reservationDto.setCancellation_date(reservation.getCancellation_date());

        return reservationDto;
    }
    public static Reservation mapToReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setReservation_id(reservationDto.getReservation_id());
        reservation.setPick_up_date(reservationDto.getPick_up_date());
        reservation.setDrop_off_date(reservationDto.getDrop_off_date());
        reservation.setActual_pick_up_date(reservationDto.getActual_pick_up_date());
        reservation.setActual_drop_off_date(reservationDto.getActual_drop_off_date());
        reservation.setBaby_seat(reservationDto.getBaby_seat());
        reservation.setInsurance(reservationDto.getInsurance());
        reservation.setDamages(reservationDto.getDamages());
        reservation.setLate_fee(reservationDto.getLate_fee());
        reservation.setTotal_charge(reservationDto.getTotal_charge());
        reservation.setRes_status(reservationDto.getRes_status());
        reservation.setRes_created_date(reservationDto.getRes_created_date());
        reservation.setRes_modified_date(reservationDto.getRes_modified_date());
        reservation.setRes_last_action(reservationDto.getRes_last_action());
        reservation.setCancellation_date(reservationDto.getCancellation_date());

        return reservation;
    }
}
