package cz.muni.fi.pa165.facade;


import cz.muni.fi.pa165.contracts.UserDTO;
import cz.muni.fi.pa165.contracts.request.CreateReservationRequest;
import cz.muni.fi.pa165.contracts.request.DeleteReservationRequest;
import cz.muni.fi.pa165.contracts.request.UpdateReservationRequest;
import cz.muni.fi.pa165.contracts.response.CreateReservationResponse;
import cz.muni.fi.pa165.contracts.response.DeleteReservationResponse;
import cz.muni.fi.pa165.contracts.response.UpdateReservationResponse;
import cz.muni.fi.pa165.enums.CarReservationState;

import java.util.List;

public interface CarReservationRequestFacade {

    CreateReservationResponse createReservation(CreateReservationRequest request);

    UpdateReservationResponse updateReservation(UpdateReservationRequest request);

    DeleteReservationResponse deleteReservation(DeleteReservationRequest request);

    void setState(CarReservationState state);

    List<UserDTO> getAllByState(CarReservationState state);

//    updateStartDate

//    updateEndDate
}
