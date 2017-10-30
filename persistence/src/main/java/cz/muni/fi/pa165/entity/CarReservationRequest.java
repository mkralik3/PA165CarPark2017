package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.CarReservationRequestState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @author Matej Kralik
 */
@Entity
public class CarReservationRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn (nullable=false)
    private Car car;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable=false)
    private User user;

    @NotNull
    @Column(nullable=false)
    private LocalDateTime reservationStartDate;

    @NotNull
    @Column(nullable=false)
    private LocalDateTime reservationEndDate;

    @NotNull
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private CarReservationRequestState state;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime creationDate;

    @NotNull
    @Column(nullable=false)
    private LocalDateTime modificationDate;

    public CarReservationRequest() {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarReservationRequest)) return false;

        CarReservationRequest that = (CarReservationRequest) o;

        if (getCar() != null ? !getCar().equals(that.getCar()) : that.getCar() != null) return false;
        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) return false;
        if (getReservationStartDate() != null ? !getReservationStartDate().equals(that.getReservationStartDate()) : that.getReservationStartDate() != null)
            return false;
        if (getReservationEndDate() != null ? !getReservationEndDate().equals(that.getReservationEndDate()) : that.getReservationEndDate() != null)
            return false;
        return getState() == that.getState();
    }

    @Override
    public int hashCode() {
        int result = getCar() != null ? getCar().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getReservationStartDate() != null ? getReservationStartDate().hashCode() : 0);
        result = 31 * result + (getReservationEndDate() != null ? getReservationEndDate().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        return result;
    }
}
