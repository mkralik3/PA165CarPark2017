package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarDTO;

import java.util.List;

public interface CarFacade {
    void createCar(CarDTO car);

    void updateCar(CarDTO car);

    void deleteCar(long id);

    CarDTO findCar(long id);

    List<CarDTO> findAllCar();
}
