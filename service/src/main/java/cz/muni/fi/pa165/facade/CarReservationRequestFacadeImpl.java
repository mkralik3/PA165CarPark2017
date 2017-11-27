package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;
import cz.muni.fi.pa165.dto.enums.*;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
@author Martin Mi�keje
*/
@Service
@Transactional
public class CarReservationRequestFacadeImpl implements CarReservationRequestFacade{

    private final Logger log = LoggerFactory.getLogger(CarReservationRequestFacadeImpl.class);

    @Inject
    private CarReservationRequestService reservationsService;
    @Inject
    private BeanMappingService beanMappingService;
    
    @Override
    public CarReservationRequestOperationResult createCarReservationRequest(CarReservationRequestDTO reservation) {
        CarReservationRequestOperationResult result = new CarReservationRequestOperationResult();
        try {
            CarReservationRequest toCreate = beanMappingService.mapTo(reservation, CarReservationRequest.class);
            Set<CarReservationRequestOperationErrorCode> errors = new HashSet<>();
            reservationsService.create(toCreate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, CarReservationRequestOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(toCreate, CarReservationRequestDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, CarReservationRequestOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(CarReservationRequestOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public CarReservationRequestOperationResult updateCarReservationRequest(CarReservationRequestDTO reservation) {
        CarReservationRequestOperationResult result = new CarReservationRequestOperationResult();
        try {
            CarReservationRequest toUpdate = beanMappingService.mapTo(reservation, CarReservationRequest.class);
            Set<CarReservationRequestOperationErrorCode> errors = new HashSet<>();
            reservationsService.update(toUpdate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, CarReservationRequestOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(toUpdate, CarReservationRequestDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, CarReservationRequestOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(CarReservationRequestOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public SimpleResult deleteCarReservationRequest(long id) {
        SimpleResult result = new SimpleResult();
        try {
            CarReservationRequest deleted = reservationsService.delete(id);
            result.setIsSuccess(deleted != null);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public List<CarReservationRequestDTO> getAllForRegionalBranch(Set<Long> regionalBranchIds, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<CarReservationRequestDTO> result = new ArrayList<>();
        try {
            List<CarReservationRequest> reservations = reservationsService.getAllForRegionalBranch(regionalBranchIds, dateFrom, dateTo);
            if (reservations != null) {
                reservations.forEach((u) -> {
                    result.add(beanMappingService.mapTo(u, CarReservationRequestDTO.class));
                });
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }

}
