package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.service.enums.CarOperationErrorCode;

import java.util.List;
import java.util.Set;

public interface CarService {


    /**
     * Create car
     * @param car car which will be created
     * @return error codes if happen
     * @throws IllegalArgumentException if car is null
     */
    Set<CarOperationErrorCode> createCar(Car car);

    /**
     * Update car
     * @param car car which will be updated
     * @return error codes if happen
     * @throws IllegalArgumentException if car is null or it is not exist
     */
    Set<CarOperationErrorCode> updateCar(Car car);

    /**
     * Delete particular car
     * @param id id of particular car
     * @return deleted car
     */
    Car deleteCar(long id);

    /**
     * Find all cars
     * @return all cars
     */
    List<Car> findAllCars();

    /**
     * Find car by id
     * @param id id of car
     * @return particular car or null
     */
    Car findCar(long id);
}
