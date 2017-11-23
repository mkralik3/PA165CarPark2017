package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.CarReservationRequestState;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarReservationRequestDTO)) return false;

        CarReservationRequestDTO that = (CarReservationRequestDTO) o;

        if (getCar() != null ? !getCar().equals(that.getCar()) : that.getCar() != null) return false;
        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) return false;
        if (getReservationStartDate() != null ? !getReservationStartDate().equals(that.getReservationStartDate()) : that.getReservationStartDate() != null)
            return false;
        return getReservationEndDate() != null ? getReservationEndDate().equals(that.getReservationEndDate()) : that.getReservationEndDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getCar() != null ? getCar().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getReservationStartDate() != null ? getReservationStartDate().hashCode() : 0);
        result = 31 * result + (getReservationEndDate() != null ? getReservationEndDate().hashCode() : 0);
        return result;
    }
}