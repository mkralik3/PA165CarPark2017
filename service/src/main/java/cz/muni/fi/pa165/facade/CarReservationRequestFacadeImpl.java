package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarReservationRequestDTO;
import cz.muni.fi.pa165.enums.CarReservationRequestState;

import java.time.LocalDateTime;
import java.util.List;

public class CarReservationRequestFacadeImpl implements CarReservationRequestFacade{
    @Override
    public void createCarReservationRequest(CarReservationRequestDTO reservation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CarReservationRequestDTO updateCarReservationRequest(CarReservationRequestDTO reservation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteCarReservationRequest(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setState(CarReservationRequestState state, long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CarReservationRequestDTO> getAllByState(CarReservationRequestState state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateStartDate(LocalDateTime startDate, long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateEndDate(LocalDateTime endDate, long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
