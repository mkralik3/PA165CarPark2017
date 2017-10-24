package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;

import java.util.Collection;

/**
 * The interface for car entity
 * @author Matej Kralik
 */
public interface CarDAO {

    /**
     * Create car in database
     * @param car - particular car
     */
    void createCar(Car car);

    /**
     * Update particular car in database
     * @param car - particular car
     * @return updated car
     */
    Car updateCar(Car car);

    /**
     * Delete particular car
     * @param car - particular car
     */
    void deleteCar(Car car);

    /**
     * Get all cars from database
     * @return collection of all cars in database
     */
    Collection<Car> findAllCars();

    /**
     * Find specific car
     * @param id - id of car
     * @return specific Car or null when car with the id doesn't exist in database
     */
    Car findCarById(Long id);
}
