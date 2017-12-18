package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.config.ApiDefinition;
import cz.muni.fi.pa165.dto.CarReservationRequestDTO;
import cz.muni.fi.pa165.dto.results.CarReservationRequestOperationResult;
import cz.muni.fi.pa165.exceptions.ResourceNotFound;
import cz.muni.fi.pa165.exceptions.ResourceNotValid;
import cz.muni.fi.pa165.facade.CarReservationRequestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(ApiDefinition.Reservation.BASE)
public class CarReservationController {

    private final static Logger logger = LoggerFactory.getLogger(CarReservationController.class);

    @Inject
    private CarReservationRequestFacade reservationRequestFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CarReservationRequestDTO> getAllReservations(){
        List<CarReservationRequestDTO> result = reservationRequestFacade.findAll();
        if(result == null) {
            result = Collections.emptyList();
        }
        return result;
    }

    @RequestMapping(value = ApiDefinition.Reservation.ID , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CarReservationRequestDTO getReservationById(@PathVariable(ApiDefinition.Reservation.PATH_ID) long id) {
        CarReservationRequestDTO result = reservationRequestFacade.findOne(id);
        if (result != null) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CarReservationRequestOperationResult createReservation(@Valid
                                                                        @RequestBody CarReservationRequestDTO reservation,
                                                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }
        logger.debug("REST create reservation: ", reservation);

        return reservationRequestFacade.createCarReservationRequest(reservation);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CarReservationRequestOperationResult updateReservation(@Valid @RequestBody CarReservationRequestDTO reservation,
                                                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }
        logger.debug("REST update reservation: ", reservation);

        return reservationRequestFacade.updateCarReservationRequest(reservation);
    }

    @RequestMapping(value = ApiDefinition.Reservation.ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteCarReservation(@PathVariable(ApiDefinition.Reservation.PATH_ID) long id){
        logger.debug("REST deleteCar with id: ", id);
        try {
            reservationRequestFacade.deleteCarReservationRequest(id);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceNotFound();
        }
    }

    @RequestMapping(value = ApiDefinition.Reservation.ID, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CarReservationRequestDTO> getReservation(@PathVariable(ApiDefinition.Reservation.PATH_ID) long id, @RequestBody Map<String, LocalDateTime> period){
        logger.debug("get all reservations for branch ", id);

        LocalDateTime start = period.get("start");
        LocalDateTime end = period.get("end");
        Set<Long> ids = new HashSet<>();
        ids.add(id);
        List<CarReservationRequestDTO> result = reservationRequestFacade.getAllForRegionalBranch(ids,start,end);
        if(result == null) {
            result = Collections.emptyList();
        }
        return result;
    }
}
