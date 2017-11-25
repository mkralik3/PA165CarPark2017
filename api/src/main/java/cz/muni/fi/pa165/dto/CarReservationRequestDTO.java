package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.dto.enums.CarReservationRequestState;

import java.time.LocalDateTime;

public class CarReservationRequestDTO {

    private Long id;

    private CarDTO car;

    private UserDTO user;

    private LocalDateTime reservationStartDate;

    private LocalDateTime reservationEndDate;

    private CarReservationRequestState state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LocalDateTime getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(LocalDateTime reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public LocalDateTime getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(LocalDateTime reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public CarReservationRequestState getState() {
        return state;
    }

    public void setState(CarReservationRequestState state) {
        this.state = state;
    }

}