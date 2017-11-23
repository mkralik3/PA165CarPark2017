package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarReservationRequestDTO;
import cz.muni.fi.pa165.enums.CarReservationRequestState;

import java.time.LocalDateTime;
import java.util.List;

public interface CarReservationRequestFacade {

    void createCarReservationRequest(CarReservationRequestDTO reservation);

    CarReservationRequestDTO updateCarReservationRequest(CarReservationRequestDTO reservation);

    void deleteCarReservationRequest(long id);

    void setState(CarReservationRequestState state, long id);

    List<CarReservationRequestDTO> getAllByState(CarReservationRequestState state);

    void updateStartDate(LocalDateTime startDate, long id);

    void updateEndDate(LocalDateTime endDate, long id);
}
