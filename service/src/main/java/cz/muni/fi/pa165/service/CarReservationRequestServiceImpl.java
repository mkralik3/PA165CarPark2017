package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CarReservationRequestDAO;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import cz.muni.fi.pa165.service.enums.CarReservationRequestOperationErrorCode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miskeje
 */
@Service
public class CarReservationRequestServiceImpl implements CarReservationRequestService {
    @Inject
    private CarReservationRequestDAO requestsDao;
    @Inject
    private TimeService timeService;
    
    @Override
    public Set<CarReservationRequestOperationErrorCode> create(CarReservationRequest request) {
        if (request == null) {
            throw new NullPointerException("request");
        }
        Set<CarReservationRequestOperationErrorCode> errors = new HashSet<>();
        errors.addAll(validateInput(request, null));
        if (errors.isEmpty()) {
            try {
                request.setState(CarReservationRequestState.CREATED);
                request.setCreationDate(timeService.getCurrentTime());
                request.setModificationDate(timeService.getCurrentTime());
                requestsDao.save(request);
            }
            catch (DataAccessException ex) {
                errors.add(CarReservationRequestOperationErrorCode.DATABASE_ERROR);
                // todo log
            }
        }
        return errors;
    }
    
    @Override
    public Set<CarReservationRequestOperationErrorCode> update(CarReservationRequest request) {
        if (request == null) {
            throw new NullPointerException("request");
        }
        // get user from db for change safety
        CarReservationRequest existing = requestsDao.findOne(request.getId());
        if (existing == null) {
            throw new IllegalArgumentException("request not exists");
        }
        Set<CarReservationRequestOperationErrorCode> errors = new HashSet<>();
        errors.addAll(validateInput(request, existing));
        if (errors.isEmpty()) {
            try {
                request.setState(CarReservationRequestState.CREATED);
                request.setCreationDate(timeService.getCurrentTime());
                request.setModificationDate(timeService.getCurrentTime());
                requestsDao.save(request);
            }
            catch (DataAccessException ex) {
                errors.add(CarReservationRequestOperationErrorCode.DATABASE_ERROR);
                // todo log
            }
        }
        return errors;
    }
    
    @Override
    public CarReservationRequest delete(long reservationRequestId) {
        CarReservationRequest user = requestsDao.findOne(reservationRequestId);
        if (user != null) {
            requestsDao.delete(user);
        }
        return user;
    }
    
    @Override
    public List<CarReservationRequest> getAllForRegionalBranch(Set<Long> regionalBranchIds, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return requestsDao.getAllForRegionalBranch(regionalBranchIds, dateFrom, dateTo);
    }

    private Set<CarReservationRequestOperationErrorCode> validateInput(CarReservationRequest newRequest, CarReservationRequest existingRequest){
        Set<CarReservationRequestOperationErrorCode> errors = new HashSet<>();
        if (newRequest.getCar() == null) {
            errors.add(CarReservationRequestOperationErrorCode.CAR_REQUIRED);
        }
        else if (newRequest.getUser() == null) {
            errors.add(CarReservationRequestOperationErrorCode.USER_REQUIRED);
        }
        else if (newRequest.getReservationStartDate() == null) {
            errors.add(CarReservationRequestOperationErrorCode.START_DATE_REQUIRED);
        }
        else if (newRequest.getReservationEndDate() == null) {
            errors.add(CarReservationRequestOperationErrorCode.END_DATE_REQUIRED);
        }
        if (errors.isEmpty()) {
            List<CarReservationRequest> overlappedReservations = requestsDao.findAllOverlappedReservations(newRequest.getReservationStartDate(), newRequest.getReservationEndDate(), newRequest.getCar().getId());
            if (existingRequest == null) {
                if (!overlappedReservations.isEmpty()) {
                    errors.add(CarReservationRequestOperationErrorCode.CAR_NOT_AVAILABLE);
                }
            } else {
                if (overlappedReservations.size() != 1 || !overlappedReservations.get(0).equals(existingRequest)) {
                    errors.add(CarReservationRequestOperationErrorCode.CAR_NOT_AVAILABLE);
                }
            }
        }
        return errors;
    }

}
