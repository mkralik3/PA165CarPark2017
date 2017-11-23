package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.entity.Car;

import java.util.List;

public interface CarService {

    void createCar(Car car);

    void updateCar(Car car);

    void deleteCar(long id);

    List<Car> findAllCar();

    Car findCar(long id);
}
