package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;

import java.util.List;

/**
 * @author Matej Kralik
 */
public interface CarFacade {

    /**
     * Create car
     * @param car car which will be created
     * @return result of operation with data
     * @throws IllegalArgumentException if car is null
     */
    CarOperationResult createCar(CarDTO car);

    /**
     * Update car
     * @param car car which will be updated
     * @return result of operation with data
     * @throws IllegalArgumentException if car is null or it is not exist
     */
    CarOperationResult updateCar(CarDTO car);

    /**
     * Delete particular car
     * @param carId id of particular car
     * @return simple result (isSuccess = true if found and deleted, otherwise false)
     */
    SimpleResult deleteCar(long carId);

    /**
     * Find car by id
     * @param carId id of car
     * @return particular car or null
     */
    CarDTO findCarById(long carId);

    /**
     * Find all cars which are activated
     * @return all cars
     */
    List<CarDTO> findAllActivatedCars();

    /**
     * Find all cars which are deactivated
     * @return all deactivated cars
     */
    List<CarDTO> findAllDeactivatedCars();
}
