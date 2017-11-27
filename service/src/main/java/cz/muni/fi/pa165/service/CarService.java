package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dto.results.CarOperationResult;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.service.enums.CarOperationErrorCode;
import sun.nio.ch.SelectorImpl;

import java.util.List;
import java.util.Set;

public interface CarService {

    Set<CarOperationErrorCode> createCar(Car car);

    Set<CarOperationErrorCode> updateCar(Car car);

    Car deleteCar(long id);

    List<Car> findAllCars();

    Car findCar(long id);
}
