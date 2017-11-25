package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;

import java.time.LocalDateTime;
import java.util.List;

public class CarReservationRequestFacadeImpl implements CarReservationRequestFacade{

    @Override
    public CarReservationRequestOperationResult createCarReservationRequest(CarReservationRequestDTO reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CarReservationRequestOperationResult updateCarReservationRequest(CarReservationRequestDTO reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SimpleResult deleteCarReservationRequest(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CarReservationRequestDTO> getAllForRegionalBranch(RegionalBranchDTO regionalBranch, Boolean includeChildren, LocalDateTime dateFrom, LocalDateTime dateTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
