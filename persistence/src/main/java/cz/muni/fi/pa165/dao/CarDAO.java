package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface for car entity
 * @author Matej Kralik
 */
@org.springframework.stereotype.Repository
public interface CarDAO extends CrudRepository<Car, Long> {

    /**
     * Get all cars from database
     * @return all cars in database
     */
    List<Car> findAll();
}
